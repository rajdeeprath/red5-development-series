package org.red5.connection.examples.messaging;

import java.util.Set;

import org.red5.server.api.service.IServiceHandlerProvider;

public class ClientServices implements IClientServices, IServiceHandlerProvider {
	
	
	private Application application;
	
	
	
	public ClientServices(){
		
	}
	
	
	
	public ClientServices(Application application){
		this.application = application;
	}

	

	public Application getApplication() {
		return application;
	}


	public void setApplication(Application application) {
		this.application = application;
	}


	@Override
	public void clientToServer() {
		application.clientToServer();
	}


	@Override
	public void callClientMethodWithoutParams() {
		application.callClientMethodWithoutParams();
	}


	@Override
	public void callClientMethodWithParams() {
		application.callClientMethodWithParams();
	}


	@Override
	public void callClientMethodWithParamsForResult() {
		application.callClientMethodWithParamsForResult();
	}



	@Override
	public void registerServiceHandler(String name, Object handler) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void unregisterServiceHandler(String name) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public Object getServiceHandler(String name) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Set<String> getServiceHandlerNames() {
		// TODO Auto-generated method stub
		return null;
	}

}
