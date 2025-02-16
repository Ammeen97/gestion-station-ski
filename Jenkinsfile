pipeline {
    agent any

    environment {
        // Define environment variables
        GIT_REPO = 'https://github.com/Ammeen97/gestion-station-ski.git'
        BRANCH = 'main'
//         SONAR_HOST_URL = 'http://your-sonarqube-server:9000'
//         SONAR_PROJECT_KEY = 'gestion-de-station-de-ski'
//         SONAR_TOKEN = credentials('sonar-token')
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from GitHub
                git branch: "${BRANCH}", url: "${GIT_REPO}"
            }
        }

        stage('Build') {
            steps {
                // Run Maven clean and install
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                // Run Maven tests
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Run SonarQube analysis
//                 withSonarQubeEnv('SonarQube') { // Configure SonarQube server in Jenkins
//                     sh 'mvn sonar:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_TOKEN}'
//                 }
                echo 'SonarQube Analysis...'
            }
        }

        stage('Deploy') {
            steps {
                // Deploy the application (example: deploy to a server or Docker)
                echo 'Deploying application...'
                // Add your deployment steps here (e.g., Docker build/push, deploy to Kubernetes, etc.)
            }
        }
    }

    post {
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed!'
        }
        always {
            // Clean up workspace after the build
            cleanWs()
        }
    }
}