//
// Fragment shader for procedural bricks
//
// Authors: Dave Baldwin, Steve Koren, Randi Rost
//          based on a shader by Darwyn Peachey
//
// Copyright (c) 2002-2004 3Dlabs Inc. Ltd. 
//
// See 3Dlabs-License.txt for license information
//

//uniform vec3  BackgroundColor, GridLineColor;
//uniform vec2  GridPeriod;
//uniform vec2  GridWidth;	//proportion of grid period

const vec4  BackgroundColor = vec4(0,0,0,0.0);
const vec4  GridLineColor = vec4(1,1,1,1.0);
const float  GridBoxes = 8;
const float  GridWidth = 0.2;	//proportion of grid period

varying vec2  MCposition;
varying float LightIntensity;

void main(void)
{
    vec4  color;
    vec2  position;
    
    position = MCposition / GridBoxes;

	float gx = MCposition.x - GridWidth/4.0;
	float gy = MCposition.y - GridWidth/4.0;
	
    float gridIntensityX =  (1 * abs(0.5 - fract(gx * GridBoxes)/GridWidth));
    gridIntensityX = exp(1 * (gridIntensityX+0.1))-1.0;
    gridIntensityX = clamp(gridIntensityX, 0.0, 1.0);

    float gridIntensityY =  (1 * abs(0.5 - fract(gy * GridBoxes)/GridWidth));
    gridIntensityY = exp(1 * (gridIntensityY+0.1))-1.0;
    gridIntensityY = clamp(gridIntensityY, 0.0, 1.0);
    
    float gridIntensity = min(gridIntensityX, gridIntensityY);
    
    color = mix(GridLineColor, BackgroundColor, gridIntensity);
    
    color *= LightIntensity;
    
    gl_FragColor = color;
}