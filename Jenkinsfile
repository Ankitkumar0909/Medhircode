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
                    } catch (Exception e) {
                        error "Gradle build failed: ${e.message}"
                    }
                }
            }
        }

        stage('Build and Save Docker Image') {
            steps {
                script {
                    def dockerImage = docker.build("${IMAGE_NAME}:${IMAGE_TAG}")
                    dockerImage.save("${TAR_FILE}")
                    echo "Docker Image Built and Saved: ${dockerImage.id}"
                }
            }
        }
    }
}
