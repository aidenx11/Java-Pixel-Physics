package com.aidenx11.JavaPixelPhysics;

public class Chunk {
	
	public boolean activeThisFrame;
	
	public boolean activeNextFrame;
	
	public boolean activeInTwoFrames;
	
	
	public Chunk() {
		activeThisFrame = false;
		activeNextFrame = false;
		activeInTwoFrames = false;
	}
	
	public void disableChunk() {
		activeNextFrame = false;
	}
	
	public void enableChunk() {
		activeNextFrame = true;
	}
	
	public void stepChunk() {
		activeThisFrame = activeNextFrame;
		activeNextFrame = activeInTwoFrames;
		activeInTwoFrames = false;
	}
}
