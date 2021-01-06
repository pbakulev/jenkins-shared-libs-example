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
                image_tag = readFile "image_tag"
                image_name = readFile "image_name"
                sh "ansible-playbook -i "localhost," /home/vagrant/deploy.yaml -e image_name=$image_name,image_tag=$image_tag"
          }
        }
      }
    }
}