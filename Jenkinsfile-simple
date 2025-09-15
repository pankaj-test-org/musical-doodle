pipeline {
    agent any

    stages {
        stage('Test') {
            steps {
                echo 'Running a simple test!'
            }
        }
        stage('Create and Archive File') {
            steps {
                sh 'echo "This is a sample output" > sample.txt'
            }
        }
    }
}