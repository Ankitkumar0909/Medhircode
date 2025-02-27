

  pipeline {
    agent any

    stages {
        stage('Clone Code') {
            steps {
                git branch: 'main', url: 'https://github.com/Ankitkumar0909/Medhircode.git'
            }
        }

        stage('Build Backend') {
            steps {
                sh 'gradle build'  // Build the backend project
            }
        }

        stage('Run Ansible to Deploy Locally') {
            steps {
                sh 'ansible-playbook -i inventory deploy_backend.yml'
            }
        }
    }
}
