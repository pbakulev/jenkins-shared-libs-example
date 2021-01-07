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
                app_name = sh "basename -s .git `git config --get remote.origin.url`"
                sh "ansible-playbook -i \"localhost,\" /home/vagrant/playbooks/deploy.yaml -e image=$image,app_name=$app_name"
          }
        }
      }
    }
  }
}