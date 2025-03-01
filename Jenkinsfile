pipeline {
    agent any

    environment {
        SERVER_IP = "192.168.0.200"
        SSH_USER = "ankitm"
        JAR_FILE = "build/libs/backend-0.0.1-SNAPSHOT.jar"
        REMOTE_PATH = "/home/ankitm/shared/backend-0.0.1-SNAPSHOT.jar"
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

        stage('Transfer JAR to Remote Server') {
            steps {
                script {
                    def status = sh(script: "scp -o StrictHostKeyChecking=no ${JAR_FILE} ${SSH_USER}@${SERVER_IP}:${REMOTE_PATH}", returnStatus: true)
                    if (status != 0) {
                        error "Failed to transfer JAR to remote server."
                    }
                }
            }
        }

        stage('Deploy to Podman on Remote Server') {
            steps {
                script {
                    def status = sh(script: """
                    ssh -T -o StrictHostKeyChecking=no ${SSH_USER}@${SERVER_IP} << 'EOF'
                    cd /home/ankitm/shared

                    echo "Creating Dockerfile..."
                    cat > Dockerfile << 'EOL'
                    FROM openjdk:23-jdk-slim
                    WORKDIR /app
                    COPY backend-0.0.1-SNAPSHOT.jar app.jar
                    EXPOSE 8080
                    CMD ["java", "-jar", "app.jar"]
                    EOL

                    echo "Stopping and removing existing container if running..."
                    sudo -u podman -i podman stop backend || true
                    sudo -u podman -i podman rm backend || true

                    echo "Building new Podman image..."
                    sudo -u podman -i podman build -t backend:latest .

                    echo "Running new Podman container..."
                    sudo -u podman -i podman run -d --name backend -p 8080:8080 backend:latest
                    EOF
                    """, returnStatus: true)

                    if (status != 0) {
                        error "Deployment to Podman failed."
                    }
                }
            }
        }
    }
}
