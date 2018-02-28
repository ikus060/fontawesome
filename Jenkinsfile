pipeline {
    environment {
        NEXUS = credentials("local-nexus")
        GITLAB = credentials("gitlab-jenkins")	
    }
    agent {
        docker {
            image 'jamesdbloom/docker-java7-maven'
            args '-v $HOME/.m2:/root/.m2'
        }
    }
    stages {
        stage ('Build') {
            steps {
                writeFile file: "settings.xml", text: "<settings><servers><server><id>patrikdufresne</id><username>${NEXUS_USR}</username><password>${NEXUS_PSW}</password></server></servers></settings>"
                sh 'mvn --settings settings.xml -Dmaven.test.failure.ignore=true clean deploy'
            }
        }
        stage ('Publish') {
            when { branch 'master' }
            steps {
                script {
                    // We want to pick up the version from the pom
                    pom = readMavenPom file: 'pom.xml'
                    version = pom.version.replace("-SNAPSHOT", "-${BUILD_NUMBER}")
                }
                sh '''
                    git reset --hard
                    git checkout master
                    git config --local user.email "jenkins@patrikdufresne.com"
                    git config --local user.name "Jenkins"
                '''
                sh "mvn --settings settings.xml -Dmaven.test.skip=true -DreleaseVersion=${version} -DdevelopmentVersion=${pom.version} -DpushChanges=false -DlocalCheckout=true -DpreparationGoals=initialize -Dresume=false release:prepare release:perform -B"
                sh "git push http://${GITLAB}@git.patrikdufresne.com/pdsl/minarca.git --tags"
            }
        }
    }
}
