pipeline {
    agent any

    environment {
        DOCKER_CMD = "/opt/homebrew/bin/docker"
        IMAGE_NAME = "backend"
        IMAGE_TAG = "latest"
        TAR_FILE = "backend.tar"
        SSH_USER = "ankitm"
        SERVER_IP = "192.168.0.200"
        SHARED_DIR = "/home/ankitm/shared"
        GIT_REPO = "https://github.com/Ankitkumar0909/Medhircode.git"
        GIT_BRANCH = "main"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: GIT_BRANCH, url: GIT_REPO
            }
        }

        stage('Build JAR File with Gradle') {
            steps {
                script {
                    try {
                        sh './gradlew clean build'
                        echo "✅ Gradle build completed successfully."
                    } catch (Exception e) {
                        error "❌ Gradle build failed: ${e.message}"
                    }
                }
            }
        }

        stage('Build and Save Docker Image') {
            steps {
                script {
                    try {
                        sh "${DOCKER_CMD} build --platform=linux/amd64 -t ${IMAGE_NAME}:${IMAGE_TAG} ."
                        sh "${DOCKER_CMD} save -o ${TAR_FILE} ${IMAGE_NAME}:${IMAGE_TAG}"
                        echo "✅ Docker Image Built and Saved: ${IMAGE_NAME}:${IMAGE_TAG}"
                    } catch (Exception e) {
                        error "❌ Docker build or save failed: ${e.message}"
                    }
                }
            }
        }

        stage('Transfer TAR File to Server') {
            steps {
                script {
                    try {
                        sh "scp ${TAR_FILE} ${SSH_USER}@${SERVER_IP}:${SHARED_DIR}/"
                        echo "✅ TAR file transferred successfully."
                    } catch (Exception e) {
                        error "❌ TAR file transfer failed: ${e.message}"
                    }
                }
            }
        }

        stage('Deploy with Podman on Server') {
            steps {
                script {
                    try {
                        sh """
                            ssh ${SSH_USER}@${SERVER_IP} "
                            set -xe;
                            sudo -u podman -i podman stop backend || true;
                            sudo -u podman -i podman rm backend || true;
                            sudo -u podman -i podman load -i ${SHARED_DIR}/${TAR_FILE};
                            sudo -u podman -i podman run -d -p 4000:4000 --name backend backend:latest;
                            echo '✅ Backend container started successfully.'
                            "
                        """
                        echo "✅ Deployment with Podman completed."
                    } catch (Exception e) {
                        error "❌ Deployment failed: ${e.message}"
                    }
                }
            }
        }

        stage('Verify Deployment') {
            steps {
                script {
                    try {
                        sh """
                            ssh ${SSH_USER}@${SERVER_IP} "
                            set -xe;
                            sudo -u podman -i podman ps -a | grep backend;
                            sudo -u podman -i podman logs backend;
                            "
                        """
                        echo "✅ Deployment verification completed."
                    } catch (Exception e) {
                        error "❌ Deployment verification failed: ${e.message}"
                    }
                }
            }
        }
    }
}
