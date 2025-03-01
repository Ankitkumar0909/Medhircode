pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "backend:latest"
        TAR_FILE = "backend.tar"
        SERVER_USER = "ankitm"
        SERVER_IP = "192.168.0.200"
        SERVER_PATH = "/home/ankitm/shared"  // Updated to store in the shared directory
    }

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/Ankitkumar0909/Medhircode.git'
            }
        }

        stage('Build JAR File with Gradle') {
            steps {
                sh 'chmod +x ./gradlew'
                sh './gradlew clean build'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${DOCKER_IMAGE} .'
            }
        }

        stage('Save Docker Image as TAR') {
            steps {
                sh 'docker save -o ${TAR_FILE} ${DOCKER_IMAGE}'
            }
        }

        stage('Transfer TAR to Server') {
            steps {
                sh 'scp ${TAR_FILE} ${SERVER_USER}@${SERVER_IP}:${SERVER_PATH}/${TAR_FILE}'
            }
        }
    }

    post {
        success {
            echo "TAR file successfully transferred to the server's shared directory!"
        }
        failure {
            echo "Process failed!"
        }
    }
}
