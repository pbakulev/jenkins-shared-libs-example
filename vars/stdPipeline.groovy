def call(String buildLine) {
    pipeline {
      agent any
      stages {
        stage('Build') {
          steps {
            sh buildLine
          }
        }
        stage('Deploy') {
          steps {
              script{
                image = readFile "$WORKSPACE/image"
                app_name = sh script: "basename -s .git `git config --get remote.origin.url`", returnStdout: true 
                sh "ansible-playbook -i \"localhost,\" $JENKINS_HOME/playbooks/deploy.yaml -e \"image=$image app_name=$app_name config_path=$JENKINS_HOME/.kube/config\""
          }
        }
      }
    }
  }
}