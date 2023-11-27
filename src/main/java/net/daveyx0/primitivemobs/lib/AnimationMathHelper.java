package net.daveyx0.primitivemobs.lib;

import net.minecraft.util.MathHelper;

public class AnimationMathHelper {
  public float b = 0.0F;
  
  public float c = 0.0F;
  
  public float d;
  
  public float e;
  
  public float f;
  
  public float h = 1.0F;
  
  public float swing(float speed, float max) {
    this.e = this.b;
    this.d = this.c;
    this.c = (float)(this.c + 3.200000047683716D);
    if (this.c < 0.0F)
      this.c = 0.0F; 
    if (this.c > speed)
      this.c = speed; 
    if (this.h < speed)
      this.h = speed; 
    this.h = (float)(this.h * 0.800000011920929D);
    this.b += this.h * 0.5F;
    float f1 = this.e + this.b - this.e;
    float f2 = this.d + this.c - this.d;
    float f3 = (MathHelper.sin(f1) + 0.5F) * f2 * max;
    return f3;
  }
}
