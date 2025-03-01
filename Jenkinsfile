pipeline {
    agent any

    environment {
        SERVER_IP = "192.168.0.200"
        SSH_USER = "ankitm"
        JAR_FILE = "build/libs/backend-0.0.1-SNAPSHOT.jar"
        IMAGE_NAME = "backend"
        IMAGE_TAG = "latest"
        TAR_FILE = "backend.tar"
        DOCKER_PATH = "/opt/homebrew/bin/docker" // Set Docker path manually
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
                    sh 'chmod +x ./gradlew'  // Ensure Gradle wrapper is executable
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
                    def status = sh(script: "${DOCKER_PATH} build -t ${IMAGE_NAME}:${IMAGE_TAG} .", returnStatus: true)
                    if (status != 0) {
                        error "Docker build failed. Check logs for details."
                    }
                }
            }
        }

        stage('Save Docker Image as TAR') {
            steps {
                script {
                    def status = sh(script: "${DOCKER_PATH} save -o ${TAR_FILE} ${IMAGE_NAME}:${IMAGE_TAG}", returnStatus: true)
                    if (status != 0) {
                        error "Failed to save Docker image as TAR."
                    }
                }
            }
        }
    }
}
