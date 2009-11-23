
uniform vec4  TLcolor, BRcolor;

varying vec2  MCposition;
varying float LightIntensity;

void main(void)
{
    vec4  color;
    vec2  position;
    
    position = MCposition;

	//  sudden flat step:
	//	float dx = step(abs(0 - position.x), 0.5 - BorderSize);
	//	float dy = step(abs(0 - position.y), 0.5 - BorderSize);

	color = mix(TLcolor, BRcolor, 0.5 + (position.x + (-position.y))/2.0); 
    color *= LightIntensity;
    gl_FragColor = color; //vec4 (color, 1.0);
}