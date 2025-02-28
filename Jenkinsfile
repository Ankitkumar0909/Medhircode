pipeline {
    agent any

    environment {
        SERVER_IP = "192.168.0.200"   // Remote server IP
        SSH_USER = "ankitm"           // SSH username
        JAR_FILE = "build/libs/myapp.jar"  // Path to JAR file after build
        REMOTE_PATH = "/home/ankitm/myapp.jar"  // Destination on remote server
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
                sh "scp -o StrictHostKeyChecking=no ${JAR_FILE} ankitm@192.168.0.200:/home/ankitm/myapp.jar"
            }
        }

       stage('Deploy to Podman on Remote Server') {
    steps {
        sh """
        ssh -o StrictHostKeyChecking=no ankit@192.168.1.100 << 'EOF'
        cd /home/ankitm/

        echo "Stopping and removing existing container..."
        podman stop myapp || true
        podman rm myapp || true

        echo "Building new image..."
        podman build -t myapp:latest -f- <<EOL
        FROM openjdk:17
        COPY myapp.jar /app.jar
        CMD ["java", "-jar", "/app.jar"]
        EOL

        echo "Running new container..."
        podman run -d --name myapp -p 4000:4000 myapp:latest
        EOF
        """
            }
        }
    }
}
