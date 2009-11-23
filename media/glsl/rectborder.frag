
uniform vec4  Color, BorderColor;
uniform float BorderSize;
uniform float BorderShade;

const float a = 1.0 - BorderSize - BorderShade/2.0;
const float b = 1.0 - BorderShade/2.0;

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

	float dx = smoothstep(a, b, abs(position.x)*2.0);
	float dy = smoothstep(a, b, abs(position.y)*2.0);
	 
    color  = mix(Color, BorderColor, max(dx, dy));
    color *= LightIntensity;
    gl_FragColor = color; //vec4 (color, 1.0);
}