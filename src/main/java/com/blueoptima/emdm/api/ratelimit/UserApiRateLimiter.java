package com.blueoptima.emdm.api.ratelimit;

import java.util.concurrent.Semaphore;

public class UserApiRateLimiter {
    private Semaphore permits;
    
    public UserApiRateLimiter(int rateLimit) {
        this.permits = new Semaphore(rateLimit);
    }
 
    public boolean tryAcquire() {
        return permits.tryAcquire();
    }
 
	public int getAvailablePermits() {
		return permits.availablePermits();
	}

	public void releasePermits(int accuiredPermits) {
		permits.release(accuiredPermits);
		
	}

}
