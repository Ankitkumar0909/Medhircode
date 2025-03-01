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
                sh './gradlew clean build'
            }
        }

        stage('Transfer JAR to Remote Server') {
            steps {
                sh "scp -o StrictHostKeyChecking=no ${JAR_FILE} ${SSH_USER}@${SERVER_IP}:${REMOTE_PATH}"
            }
        }

        stage('Deploy to Podman on Remote Server') {
            steps {
                sh """
                ssh -o StrictHostKeyChecking=no ${SSH_USER}@${SERVER_IP} << EOF
                cd /home/ankitm/shared

                echo "Creating Dockerfile..."
                echo 'FROM openjdk:17
                COPY backend-0.0.1-SNAPSHOT.jar /backend-0.0.1-SNAPSHOT.jar
                CMD ["java", "-jar", "/backend-0.0.1-SNAPSHOT.jar"]' > Dockerfile

                echo "Stopping and removing existing container..."
                sudo -u podman -i podman stop backend || true
                sudo -u podman -i podman rm backend || true

                echo "Building new image..."
                sudo -u podman -i podman build -t backend:latest .

                echo "Running new container..."
                sudo -u podman -i podman run -d --name backend -p 4000:4000 backend:latest
                EOF
                """
            }
        }
    }
}
