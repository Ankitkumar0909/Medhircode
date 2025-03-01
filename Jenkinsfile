pipeline {
    agent any

    environment {
        SERVER_IP = "192.168.0.200"
        SSH_USER = "ankitm"
        JAR_FILE = "build/libs/backend-0.0.1-SNAPSHOT.jar"
        IMAGE_NAME = "backend"
        IMAGE_TAG = "latest"
        TAR_FILE = "backend.tar"
    }

    stages {
        stage('Extract Code') {
            steps {
                git branch: 'main', url: 'https://github.com/Ankitkumar0909/Medhircode.git'
            }
        }

        stage('Build JAR File with Gradle') {
            steps {
                script {
                    def status = sh(script: './gradlew clean build', returnStatus: true)
                    if (status != 0) {
                        error "Build failed. Check logs for details."
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def status = sh(script: "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .", returnStatus: true)
                    if (status != 0) {
                        error "Docker image build failed."
                    }
                }
            }
        }

        stage('Save Docker Image as TAR') {
            steps {
                script {
                    def status = sh(script: "docker save -o ${TAR_FILE} ${IMAGE_NAME}:${IMAGE_TAG}", returnStatus: true)
                    if (status != 0) {
                        error "Failed to save Docker image as TAR."
                    }
                }
            }
        }
    }
}
