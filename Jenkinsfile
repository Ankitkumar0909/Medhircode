
pipeline {
    agent any

    environment {
        SERVER_IP = "192.168.0.200"  // Remote server IP
        APP_NAME = "myapp"
        IMAGE_NAME = "myapp:latest"
        TAR_FILE = "myapp.tar"
    }

    stages {
        stage('Extract Code') {
            steps {
                git 'https://github.com/Ankitkumar0909/Medhircode.git'
            }
        }

        stage('Build JAR File with Gradle') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Build Podman Image') {
            steps {
                script {
                    podmanBuild image: “${Medhir_code}”, contextPath: '.', buildArgs: ''
                }
            }
        }

        stage('Save Image as TAR File') {
            steps {
                sh "podman save -o ${TAR_FILE} ${Medhir_code}”
            }
        }

        stage('Upload TAR to Remote Server') {
            steps {
                sh "scp ${TAR_FILE} ankitm@${192.168.0.200}:/home/user/"
            }
        }

        stage('Deploy Using Ansible') {
            steps {
                ansiblePlaybook credentialsId: 'ansible-ssh-key', inventory: 'inventory.ini', playbook: 'deploy.yml'
            }
        }
    }
}
