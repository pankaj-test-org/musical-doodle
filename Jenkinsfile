pipeline {
    agent any
    environment {
        FAIL_THIS_BUILD = 'false'
    }
    stages {
        stage('Stage-0') {
            steps {
                echo 'Stage-0: deterministic mock load (sleep 3)'
                sh 'sleep 3'
            }
        }

        stage('Stage-1') {
            parallel {
                stage('Stage-1.1') {
                    steps {
                        echo "Stage-1.1: first step"
                        sh 'sleep 3'
                        echo "Stage-1.1: Execution completed!!!!!!!!!"
                    }
                }

                stage('Stage-1.2') {
                    stages {
                        stage('1.2.1') {
                            steps {
                                echo "Stage-1.2.1: deterministic mock load (sleep 9)"
                                sh 'sleep 1'
                            }
                        }
                        stage('1.2.2') {
                            steps {
                                echo "Stage-1.2.2: deterministic mock load (sleep 9)"
                                sh 'sleep 2'
                            }
                        }
                    }
                }
            }
        }

        stage('Stage - 2- Parallel-Block') {
            parallel {
                stage('Stage-2.1 - Parallel Stage') {
                    steps {
                        echo "Stage-2.1: deterministic mock load (sleep 5)"
                        sh 'sleep 2'
                    }
                }
                stage('Stage 2.2 - Test - Parallel stage') {
                    steps {
                        echo 'Running unit tests...'
                        // if you want the tests to have a fixed duration replace with a deterministic command
                        sh 'sleep 1'
                        echo 'End of mockLoads'
                        // Add actual unit test commands (mvn test) if you want them here
                    }
                }
            }
        }

        stage("Stage - 3 - Registering") {
            steps {
                echo 'Next step will register the metadata'
                registerBuildArtifactMetadata(
                        name: "build-artifact",
                        version: "1.0.0",
                        type: "jar",
                        url: "https://pankajy-dev.me/job/mbp/job/main/1/artifact/target/app.jar/*view*/",
                        digest: "6f637064707039346163663237383938",
                        label: "prod"
                )
            }
        }

        stage('Stage - 4 - Archive artifacts') {
            steps {
                echo 'Compiling the project...'
                sh 'mkdir -p target && echo "dummy jar content" > target/app.jar'
                archiveArtifacts artifacts: 'target/*.jar'
                echo 'Artifact generated: target/app.jar'
            }
        }

        stage('Stage - 5 - clean compile') {
            steps {
                echo 'Starting build stage...'
                sh 'mvn -B clean compile'
                echo 'Build stage completed.'
            }
        }

        stage('Stage - 6 - mvn test run') {
            steps {
                echo 'Starting test stage...'
                sh 'mvn -B test'
                echo 'Test stage completed.'
            }
        }

        stage('Stage - 7 - Archive Test Results') {
            steps {
                echo 'Archiving test results...'
                junit '**/target/surefire-reports/*.xml'
                echo 'Test results archived.'
            }
        }

        stage('Stage - 8 - Security Scan') {
            steps {
                // Pretend to trigger a Security Scan
                sh "echo 'This stage will work fine , with multiple scan results'"
                sh "pwd"
                sh "echo 'Security scan result for security-scan-results-s8-b.sarif' > security-scan-results-s8-a.sarif && ls -l security-scan-results-s8-a.sarif"
                sh "echo 'Security scan result for security-scan-results-s8-b.sarif' > security-scan-results-s8-b.sarif && ls -l security-scan-results-s8-b.sarif"
                // Prepare the security scan for sending
                registerSecurityScan artifacts: "security-scan-results-s8-*.sarif", format: "sarif", scanner: "sonarqube"
            }
        }

        stage('Stage - 9 - Security Scan') {
            steps {
                // Pretend to trigger a Security Scan
                sh "echo 'This stage will cause duplicate files due to archiveArtifact step in pipeline'"
                sh "pwd"
                sh "echo 'Security scan result for security-scan-results-s9-a.sarif' > security-scan-results-s9-a.sarif && ls -l security-scan-results-s9-a.sarif"
                sh "echo 'Security scan result for security-scan-results-s9-b.sarif' > security-scan-results-s9-b.sarif && ls -l security-scan-results-s9-b.sarif"
                // This will not fail, it will send array of two files
                archiveArtifacts artifacts: 'security-scan-results-s9-a.sarif'
                archiveArtifacts artifacts: 'security-scan-results-s9-b.sarif'
                // Prepare the security scan for sending
                registerSecurityScan artifacts: "security-scan-results-s9-*.sarif", format: "sarif", scanner: "sonarqube"
            }
        }

        stage('Stage - 10 - Security Scan archive true') {
            steps {
                // Pretend to trigger a Security Scan
                sh "echo 'This stage will work fine , with multiple scan results and archive true'"
                sh "pwd"
                sh "echo 'Security scan result for security-scan-results-s10a.snyk' > security-scan-results-s10a.snyk && ls -l security-scan-results-s10a.snyk"
                archiveArtifacts artifacts: 'security-scan-results-s10a.snyk'
                // Prepare the security scan for sending
                registerSecurityScan artifacts: "security-scan-results-s10a.snyk", format: "snyk" , archive: true, scanner: "snyk"
            }
        }

        // Only uncomment to check fail case
//        stage('Stage - 11 - Security Scan - Will Fail') {
//            steps {
//                // Pretend to trigger a Security Scan
//                sh "echo 'This stage will fail as the pattern will give two files with same name and different extensions'"
//                sh "pwd"
//                sh "echo 'Security scan result' > security-scan-results-s11.csv && ls -l security-scan-results-s11.csv"
//                sh "echo 'Security scan result' > security-scan-results-s11.sarif && ls -l security-scan-results-s11.sarif"
//                // Prepare the security scan for sending
//                // UPDATED PATTERN: to target specific file types
//                // This will now correctly target the SARIF file only
//                registerSecurityScan artifacts: "security-scan-results-s11*", format: "sarif", scanner: "sonarqube"
//            }
//        }

        stage('Stage - 12 - Security Scan Default') {
            steps {
                // Pretend to trigger a Security Scan
                sh "echo 'This stage will work fine , default scan results'"
                sh "pwd"
                sh "echo 'Security scan result for security-scan-results-s12a.sarif' > security-scan-results-s12a.sarif && ls -l security-scan-results-s12a.sarif"
                archiveArtifacts artifacts: 'security-scan-results-s12a.sarif'
            }
        }

    }
    post {
        always {
            echo "Pipeline finished with status: ${currentBuild.currentResult}"
        }
    }
}
