/*
 * 
 */

model Bug

global {
	int nbClass <-5;
	int nbBugs <-50;
    init {
        create bug number: nbBugs{
        	set attribute <- rnd(nbClass);
        	set color <- color hsb_to_rgb ([attribute/nbClass,1.0,1.0]);
        }
    }
} 

environment{
}

entities {
    species bug skills: [moving] {
    	 rgb color;
         int attribute;
    
    	 reflex move{
    	 	do move;
    	 }
    	 
    	reflex update{
    		set attribute <- rnd(nbClass);
    	} 
    	//Display the bug in red
        aspect base {
            draw sphere(2) color: rgb('red');
        }
        
        //Display the bug with a color that represent the value of the attribute
        aspect attribute{
          draw sphere(2) color: color;
          set color <- color hsb_to_rgb ([attribute/nbClass,1.0,1.0]);
        }
    }
}

output {
     /*display bug_display type:opengl ambiant_light:0.2{
        species bug aspect: base;
        species bug aspect: attribute position: {125,0,0};
    }*/
}

