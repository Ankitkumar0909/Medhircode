

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
                sh '/opt/homebrew/bin/gradle build'  // Build the backend project
            }
        }

        stage('Run Ansible to Deploy Locally') {
            steps {
                sh '/opt/homebrew/bin/ansible-playbook -i inventory deploy_backend.yml'
            }
        }
    }
}
