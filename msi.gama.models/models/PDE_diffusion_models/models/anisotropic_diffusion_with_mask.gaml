/**
 *  diffusion
 *  Author: bgaudou
 *  Description: 
 */

model diffusion

global {
	int grid_size <- 51;
  	geometry shape <- envelope(square(grid_size) * 10);
  	
  	matrix mymask<- file("../includes/mask.bmp") as_matrix({grid_size,grid_size});
  	matrix<float> math_diff <- matrix([
									[1/9,1/9,1/9],
									[1/9,1/9,1/9],
									[1/9,1/9,1/9]]);

	reflex new_Value {
		ask(cells where ((each.grid_x = int(grid_size/2)) and (each.grid_y = int(grid_size/2)))){
			phero <- 1.0;
		}
	}

	reflex diff {
		diffusion var: phero on: cells mat_diffu: math_diff mask: mymask;	
	}
}

entities {
	grid cells height: grid_size width: grid_size {
		float phero <- 0.0;
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
