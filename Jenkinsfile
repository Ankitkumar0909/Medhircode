pipeline {
    agent any

    environment {
        SERVER_IP = "192.168.0.200"
        SSH_USER = "ankitm"
        JAR_FILE = "build/libs/backend-0.0.1-SNAPSHOT.jar"
        IMAGE_NAME = "backend"
        IMAGE_TAG = "latest"
        TAR_FILE = "backend.tar"
        DOCKER_CMD = "/opt/homebrew/bin/docker" // Updated Docker Path
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/Ankitkumar0909/Medhircode.git'
            }
        }

        stage('Build JAR File with Gradle') {
            steps {
                script {
                    try {
                        sh './gradlew clean build'
                        echo "Gradle build completed successfully."
                    } catch (Exception e) {
                        error "Gradle build failed: ${e.message}"
                    }
                }
            }
        }

        stage('Build and Save Docker Image') {
            steps {
                script {
                    try {
                        sh "${DOCKER_CMD} build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
                        sh "${DOCKER_CMD} save -o ${TAR_FILE} ${IMAGE_NAME}:${IMAGE_TAG}"
                        echo "Docker Image Built and Saved: ${IMAGE_NAME}:${IMAGE_TAG}"
                    } catch (Exception e) {
                        error "Docker build or save failed: ${e.message}"
                    }
                }
            }
        }
    }
}
