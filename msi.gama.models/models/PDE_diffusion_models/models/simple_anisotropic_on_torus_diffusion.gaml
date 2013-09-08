/**
 *  diffusion
 *  Author: bgaudou
 *  Description: 
 */

model diffusion

global torus: true {
	int taille <- 51;
  	geometry shape <- envelope(square(taille) * 10);
  	
  	matrix<float> math_diff <- matrix([
									[4/9,2/9,0/9],
									[2/9,1/9,0.0],
									[0/9,0.0,0.0]]);

	reflex new_Value {
		ask(cells where ((each.grid_x = int(taille/2)) and (each.grid_y = int(taille/2)))){
			phero <- 1.0;
		}
	}

	reflex diff {
		diffusion var: phero on: cells mat_diffu: math_diff;	
	}
}

entities {
	grid cells height: taille width: taille torus: false {
		float phero  <- 0.0;
		rgb color <- hsb(phero,1.0,1.0) update: hsb(phero,1.0,1.0);
		
	 	aspect default {
	 		draw shape color: color depth: phero * 100;	 		
		} 
	} 
}

experiment diffusion type: gui {
	output {
		display a type: opengl {
			species cells aspect: default;
		}
	}
}
