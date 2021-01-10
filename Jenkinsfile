pipeline {
  agent {
	
    kubernetes {
      label 'demo-app'  // all your pods will be named with this prefix, followed by a unique id
      idleMinutes 5  // how long the pod will live after no jobs have run on it
      yamlFile 'build-pod.yaml'  // path to the pod definition relative to the root of our project 
      defaultContainer 'maven'  // define a default container if more than a few stages use it, will default to jnlp container
    }
  }
  stages {
	
    stage('Compile') {
      steps {  // no container directive is needed as the maven container is the default
        sh "mvn clean compile"   

      }
      
    }
 stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
 stage('Static Code Analysis') {
            steps {
                sh 'echo "sonarqube"'
            }
        }
    stage('Package') {
      steps {  // no container directive is needed as the maven container is the default
        sh "mvn clean package"  
    }
  }
    stage('Push Image') {
      steps{
	container('docker') {
		script{
	docker.withRegistry('https://registry.hub.docker.com'  , 'docker') {   	
	  def myImg = docker.image('kirtiazad11111/test')
		 sh "docker build  -t ${myImg.imageName()}:${env.BUILD_NUMBER} ."
  			// or docker.build, etc.
 		 sh "docker push  ${myImg.imageName()}:${env.BUILD_NUMBER}"

          }
		}
        }
      }
    }
	 
	   stage('Image Scan XRAY') {
            steps {
                sh 'echo "XRAY IMAGE SCAN"'
            }
        }
  
     stage('Deploy Dev') {
            steps {
		    container('maven') {
            script {
          withCredentials([ string(credentialsId: 'kubeconfig', variable: 'kubeconfig') ]) {
		  sh 'echo  $kubeconfig | sed "s/ //g" | base64 --decode > config'
		  sh 'curl -LO "https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl" && chmod +x ./kubectl && mv ./kubectl /usr/local/bin/kubectl'
		 
          
        }
		  sh '''sed -i 's/${buildnumber}/\'"$BUILD_NUMBER"'/g' ./overlays/staging/kustomization.yaml'''
		  sh "kubectl apply -k ./overlays/staging/  --kubeconfig=config"
	    }
		    }
            
        }
  }
	    stage('Approval Stage') {
             agent none
          steps {
             input "Does the Dev environment look ok?"
          }
        }
	  
	     stage('Deploy Stage') {
            steps {
		    container('maven') {
            script {
          withCredentials([ string(credentialsId: 'kubeconfig', variable: 'kubeconfig') ]) {
		  sh 'echo  $kubeconfig | sed "s/ //g" | base64 --decode > config'
		  sh ''' if ! command -v kubectl &> /dev/null
		       then
		  curl -LO "https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl" && chmod +x ./kubectl && mv ./kubectl /usr/local/bin/kubectl
		  fi
		  '''
		 
          
        }
		  sh '''sed -i 's/${buildnumber}/\'"$BUILD_NUMBER"'/g' ./overlays/staging/kustomization.yaml'''
		  sh "kubectl apply -k ./overlays/staging/  --kubeconfig=config"
	    }
		    }
            
        }
  }  
	
		    stage('Approval Prod') {
             agent none
          steps {
             input "Does the Dev environment look ok?"
          }
        }
	  
	     stage('Deploy Prod') {
            steps {
		    container('maven') {
            script {
          withCredentials([ string(credentialsId: 'kubeconfig', variable: 'kubeconfig') ]) {
		  sh 'echo  $kubeconfig | sed "s/ //g" | base64 --decode > config'
		  sh ''' if ! command -v kubectl &> /dev/null
		       then
		  curl -LO "https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl" && chmod +x ./kubectl && mv ./kubectl /usr/local/bin/kubectl
		  fi
		  '''	 
          
        }
		  sh '''sed -i 's/${buildnumber}/\'"$BUILD_NUMBER"'/g' ./overlays/staging/kustomization.yaml'''
		  sh "kubectl apply -k ./overlays/staging/  --kubeconfig=config"
	    }
		    }
            
        }
  }  
	  
	  
  }
      post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }

}
