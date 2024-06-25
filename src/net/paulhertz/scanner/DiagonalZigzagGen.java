package net.paulhertz.scanner;

import java.util.ArrayList;

public class DiagonalZigzagGen extends PixelMapGen {
	/*
	 * See instance variables used or generated by this class in abstract class PixelMapGen.
	 * The description variable should shadow the abstract class variable with a description specific to the child class.
	 */
	public final static String description = "DiagonalZigzagGen generates a diagonal zigzag map starting at (0,0). Moves first to (0,1) then proceeeds diagonally from edge to edge. "
										   + "Any width and height greater than 0 are valid for the constructor DiagonalZigzagGen(int width, int height).";



	public DiagonalZigzagGen(int width, int height) {
		super(width, height);
		this.generate();
	}


	@Override
	public String describe() {
		return DiagonalZigzagGen.description;
	}

	/**
	 * Always returns true for width and height greater than 2.
	 */
	@Override
	public boolean validate(int width, int height) {
		// any width and height > 2 will work
		if (width < 1 || height < 1) {
			System.out.println("width and height must be greater than 0.");
			return false;
		}
		return true;
	}

	/**
	 * Initialize this.coords, this.pixelMap, this.sampleMap.
	 * @return  this.pixelMap, the value for PixelAudioMapper.signalToImageLUT.
	 */
	@Override
	public int[] generate() {
		int p = 0;
		int i = 0;
		this.pixelMap = new int[this.h * this.w];
		this.sampleMap = new int[this.h * this.w];
		this.coords = this.generateCoordinates();
		for (int[] loc : this.coords) {
			p = loc[0] + loc[1] * w;
			this.pixelMap[i++] = p;
		}
		for (i = 0; i < w * h - 1; i++) {
			this.sampleMap[this.pixelMap[i]] = i;
		}
		return pixelMap;
	}

	/**
	 * Generically-named method that calls the custom coordinate generation method (here, generateZigzagDiagonalCoordinates).
	 * Consider putting additional initializations here, if required by your coordinate generation method,
	 * rather than in the generate() method, which will then only handle coords initialization and the
	 * built-in pixelMap and sampleMap initializations.
	 *
	 * @return 	An ArrayList<int[]> of bitmap coordinates in the order the signal mapping would visit them.
	 *
	 */
	private ArrayList<int[]> generateCoordinates() {
		return this.generateZigzagDiagonalCoordinates(this.w, this.h);
	}

	/**
	 * The coordinate generation method for this class. Both lookup tables are derived from the coordinate list created
	 * by this method.
	 *
	 * @param   width		width of the 2D bitmap pixel array
	 * @param   height		height of the 2D bitmap pixel array
	 * @return 				an array of coordinate pairs
	 */
	private ArrayList<int[]> generateZigzagDiagonalCoordinates(int width, int height) {
		ArrayList<int[]> coordinates = new ArrayList<int[]>(width * height);
		int x = 0, y = 0;
		boolean movingUp = false;
		while (x < width && y < height) {
			coordinates.add(new int[]{x, y});
			if (movingUp) {                  	// movingUp is true, diagonal step is x++, y--
				if (x == width - 1) {          	// we hit the right edge
					y++;                        // move down 1
					movingUp = false;           // flip the diagonal direction
				}
				else if (y == 0) {             	// we hit the top edge
					x++;                        // move right 1
					movingUp = false;           // flip the diagonal direction
				}
				else {                          // diagonal step
					x++;                        // move right 1
					y--;                        // move up 1
				}
			}
			else {                            	// movingUp is false,  diagonal step is x--, y++
				if (y == height - 1) {          // we hit the bottom edge
					x++;                        // move right 1
					movingUp = true;            // flip the diagonal direction
				}
				else if (x == 0) {              // we hit the left edge
					y++;                        // move down 1
					movingUp = true;            // flip the diagonal direction
				}
				else {                          // diagonal step
					x--;                        // move left 1
					y++;                        // move down 1
				}
			}
		}
		return coordinates;
	}


	/* ------------------------------ GETTERS AND NO SETTERS ------------------------------ */
	/*                                                                                      */
	/*                  See abstract class PixMapGen for additional methods                 */
	/*                                                                                      */
	/* These include: getWidth(), getHeight(), getSize(), getPixelMap(), getPixelMapCopy(), */
	/* getSampleMap(), get SampleMapCopy(), getCoordinates(), getCoordinatesCopy().         */
	/*                                                                                      */
	/* ------------------------------------------------------------------------------------ */


}
