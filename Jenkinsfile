pipeline {
    agent any

    environment {
        SERVER_IP = "192.168.0.200"   // Remote server IP
        SSH_USER = "ankitm"           // SSH username
        JAR_FILE = "build/libs/backend-0.0.1-SNAPSHOT.jar"  // Path to JAR file after build
        REMOTE_PATH = "/home/ankitm/shared/backend-0.0.1-SNAPSHOT.jar"  // Destination on remote server
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
                sh "scp -o StrictHostKeyChecking=no ${JAR_FILE} ankitm@192.168.0.200:/home/ankitm/shared/backend-0.0.1-SNAPSHOT.jar"
            }
        }

       stage('Deploy to Podman on Remote Server') {
    steps {
        sh """
        ssh -o StrictHostKeyChecking=no ankitm@192.168.0.200 << 'EOF'
          cd /home/ankitm/shared

          echo "Creating Dockerfile..."
          cat <<EOL > Dockerfile
          FROM openjdk:17
          COPY backend-0.0.1-SNAPSHOT.jar /backend-0.0.1-SNAPSHOT.jar
          CMD ["java", "-jar", "/backend-0.0.1-SNAPSHOT.jar"]
          EOL
          EOF

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
