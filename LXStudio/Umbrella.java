/*
Copyright 2019 Tinic Uro

Permission is hereby granted, free of charge, to any person obtaining a
copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.tinic.airpusher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

import heronarts.lx.model.LXPoint;

import org.tinic.airpusher.AirpusherFixture;
import org.tinic.airpusher.AirpusherPattern;

public class Umbrella  extends AirpusherFixture {

  private Gradient rainbowGradient;
  private Gradient rainbowGradientBright;
  private Gradient rainyGradient;
  private Gradient autumGradient;
  private Gradient winterGradient;
  private Gradient happyGradient;
  private Gradient eveningGradient;
  private Gradient desertDream;
  private Gradient inTheJungle;
  private Gradient darkLight;
  
  public static double  easeInOutQuad(double t,double b , double c, double d) {
    if ((t/=d/2) < 1) return c/2*t*t + b;
    return -c/2 * ((--t)*(t-2) - 1) + b;
  }

  public LXFloat4 calc(AirpusherPattern.Effect effect, int LEDindex, double time, LXFloat4 glob_pos) { 
    
      double segment = 100;
      int timeI = (int)(time / segment);
      time = time % segment;
      time = easeInOutQuad(time / 100, 0.0, 1.0, 1.0) * 100;
      if ((timeI & 1) == 1) {
          time = segment - time;
      }
      
    
      switch (effect) {
          case Spring: {
            double x = Math.sin((toLocal(glob_pos).x + 1.0) * 0.25 + time * 0.050);
            double y = Math.cos((toLocal(glob_pos).y + 1.0) * 0.25 + time * 0.055);
            double l = 1.0 - toLocal(glob_pos).len() + 0.5;
            return rainbowGradientBright.reflect(x * y).mul(l).clamp().gamma();
          }
          case Summer: {
            double x0 = Math.sin((glob_pos.x + 1.0) * 0.5 + time * 0.050);
            double y0 = Math.cos((glob_pos.y + 1.0) * 0.5 + time * 0.055);
            double x1 = Math.sin((glob_pos.x + 1.0) * 10 + time * 0.50);
            double y1 = Math.cos((glob_pos.y + 1.0) * 10 + time * 0.55);
            return rainbowGradient.reflect(x0 * y0).add(new LXFloat4(1.0,1.0,1.0).mul(x1 * y1).clamp()).clamp();
          } 
          case Autum: {
            double x0 = Math.sin((glob_pos.x + 1.0) * 0.5 + time * 0.050);
            double y0 = Math.cos((glob_pos.y + 1.0) * 0.5 + time * 0.055);
            double x1 = Math.sin((glob_pos.x + 1.0) * 15 + time * 0.50);
            double y1 = Math.cos((glob_pos.y + 1.0) * 15 + time * 0.55);
            return rainyGradient.clamp(x1 * y1).add(autumGradient.reflect(x0 * y0).mul(new LXFloat4(0.5,0.5,0.5))).clamp();
          } 
          case Winter: {
            double x0 = Math.sin((glob_pos.x + 1.0) * 0.5 + time * 0.050);
            double y0 = Math.cos((glob_pos.y + 1.0) * 0.5 + time * 0.055);
            double x1 = Math.sin((toLocal(glob_pos).x + 1.0) * 0.25 + time * 0.050);
            double y1 = Math.cos((toLocal(glob_pos).y + 1.0) * 0.25 + time * 0.055);
            double l = 1.0 - toLocal(glob_pos).len() + 0.5;
            return winterGradient.reflect(x1 * y1).mul(l).mul(rainyGradient.reflect(x0 * y0)).clamp().gamma();
          } 
          case AfterRain: {
            double b = (Math.sin(glob_pos.x * 4.0 + time * 0.20) + Math.cos(glob_pos.y * 4.0 + time * 0.20)) * 0.25;
            LXFloat4 pos = glob_pos.rotate2d(time * 0.20).add(new LXFloat4(time * 0.20, 0.0, 0.0, 0.0)).mul(0.05);
            return rainbowGradientBright.repeat(pos.x).add(new LXFloat4(b,b,b,b)).clamp().gamma();
          }
          case SunsetSunrise: {
            double a = Math.max(0.0, Math.cos(glob_pos.x + Math.sin(time * 0.10))+Math.sin(glob_pos.y + Math.cos(time* 0.10))-1.0);
            LXFloat4 pos = glob_pos.rotate2d(time * 0.30).add(new LXFloat4(time * 0.30, 0.0, 0.0, 0.0)).mul(0.05);
            double l = 1.0 - toLocal(glob_pos).len() + 0.5;
            LXFloat4 c0 = happyGradient.reflect(pos.x).mul(l).clamp().gamma();
            LXFloat4 c1 = eveningGradient.clamp(a);
            return LXFloat4.lerp(c0, c1, a);
          }
          case DesertDream: {
            double x0 = Math.sin((glob_pos.x + 1.0) * 0.5 + time * 0.050);
            double y0 = Math.cos((glob_pos.y + 1.0) * 0.5 + time * 0.055);
            double x1 = Math.sin((toLocal(glob_pos).x + 1.0) * 0.25 + time * 0.050);
            double y1 = Math.cos((toLocal(glob_pos).y + 1.0) * 0.25 + time * 0.055);
            double l = 1.0 - toLocal(glob_pos).len() + 0.5;
            return desertDream.reflect(x1 * y1).mul(l).add(desertDream.reflect(x0 * y0)).clamp().gamma();
          } 
          case InTheJungle: {
            double a = Math.max(0.0, Math.cos(glob_pos.x + Math.sin(time * 0.10))+Math.sin(glob_pos.y + Math.cos(time* 0.10))-1.0);
            LXFloat4 pos = glob_pos.rotate2d(time * 0.30).add(new LXFloat4(time * 0.30, 0.0, 0.0, 0.0)).mul(0.05);
            double l = 1.0 - toLocal(glob_pos).len() + 0.5;
            LXFloat4 c0 = inTheJungle.reflect(pos.x).mul(l).clamp().gamma();
            LXFloat4 c1 = darkLight.clamp(a);
            return LXFloat4.lerp(c0, c1, a);
          }
          case TestStrip: {
              int led = (int)(time * 10.0);
              led %= leds.size();
             return new LXFloat4(1.0, 1.0, 1.0).mul(led == LEDindex ?  1.0 : 0.0);
          }
      }
      return glob_pos;    
  }

  public void initGradients() {
    
    //
    // Go https://cssgradient.io/ to create gradients the easiest way
    //

    LXFloat4[] rainbowGradient = {
       new LXFloat4(0.0, 1.0, 1.0, 0.00),
       new LXFloat4(1.0, 1.0, 1.0, 1.00)
    };

    this.rainbowGradient = new Gradient(rainbowGradient, Gradient.ColorMode.HSV);

    LXFloat4[] rainbowGradientBright = {
       new LXFloat4(0xff0000, 0.00),
       new LXFloat4(0xffbd96, 0.10),
       new LXFloat4(0xffff00, 0.17),
       new LXFloat4(0xc3ffa9, 0.25),
       new LXFloat4(0x00ff00, 0.33),
       new LXFloat4(0xd1ffbf, 0.38),
       new LXFloat4(0xaffff3, 0.44),
       new LXFloat4(0x29fefe, 0.50),
       new LXFloat4(0x637eff, 0.59),
       new LXFloat4(0x0000ff, 0.67),
       new LXFloat4(0x9c3fff, 0.75),
       new LXFloat4(0xff00ff, 0.83),
       new LXFloat4(0xffc2b0, 0.92),
       new LXFloat4(0xff0000, 1.00)
    };

    this.rainbowGradientBright = new Gradient(rainbowGradientBright, Gradient.ColorMode.RGB);

    LXFloat4[] rainyGradient = {
       new LXFloat4(0x000000, 0.00),
       new LXFloat4(0x413a40, 0.20),
       new LXFloat4(0x65718a, 0.40),
       new LXFloat4(0x6985b9, 0.53),
       new LXFloat4(0xffffff, 1.00)
    };

    this.rainyGradient = new Gradient(rainyGradient, Gradient.ColorMode.RGB);

    LXFloat4[] autumGradient = {
       new LXFloat4(0x000000, 0.00),
       new LXFloat4(0x351e10, 0.13),
       new LXFloat4(0x58321a, 0.25),
       new LXFloat4(0x60201e, 0.41),
       new LXFloat4(0x651420, 0.56),
       new LXFloat4(0x7b5a54, 0.70),
       new LXFloat4(0x9abf9e, 0.83),
       new LXFloat4(0xffffff, 1.00)
    };

    this.autumGradient = new Gradient(autumGradient, Gradient.ColorMode.RGB);

    LXFloat4[] winterGradient = {
       new LXFloat4(0xa3eed6,0.00),
       new LXFloat4(0xdcbcd4,0.21),
       new LXFloat4(0xff96d0,0.39),
       new LXFloat4(0xcb81d6,0.65),
       new LXFloat4(0x4b51f5,1.00)
    };

    this.winterGradient = new Gradient(winterGradient, Gradient.ColorMode.RGB);

    LXFloat4[] happyGradient = {
       new LXFloat4(0x22c1c3,0.00),
       new LXFloat4(0x4387c0,0.33),
       new LXFloat4(0xbb6161,0.66),
       new LXFloat4(0xfdbb2d,1.00)
    };

    this.happyGradient = new Gradient(happyGradient, Gradient.ColorMode.RGB);

    LXFloat4[] eveningGradient = {
       new LXFloat4(0x000000,0.00),
       new LXFloat4(0x4387c0,0.80),
       new LXFloat4(0xbb6161,0.90),
       new LXFloat4(0xff9500,0.95),
       new LXFloat4(0xffffff,1.00)
    };

    this.eveningGradient = new Gradient(eveningGradient, Gradient.ColorMode.RGB);

    LXFloat4[] desertDream = {
       new LXFloat4(0x4d5951,0.00),
       new LXFloat4(0x372a25,0.19),
       new LXFloat4(0x863c25,0.41),
       new LXFloat4(0xa15123,0.63),
       new LXFloat4(0xd6aa68,0.84),
       new LXFloat4(0xf7d6b4,1.00)
    };

    this.desertDream = new Gradient(desertDream, Gradient.ColorMode.RGB);

    LXFloat4[] inTheJungle = {
       new LXFloat4(0x135e46,0.00),
       new LXFloat4(0x478966,0.20),
       new LXFloat4(0x73a788,0.40),
       new LXFloat4(0xe3c6ad,0.70),
       new LXFloat4(0xd09d7b,0.90),
       new LXFloat4(0xb67b65,1.00)
    };

    this.inTheJungle = new Gradient(inTheJungle, Gradient.ColorMode.RGB);

    LXFloat4[] darkLight = {
       new LXFloat4(0x000000,0.00),
       new LXFloat4(0x135e46,0.50),
       new LXFloat4(0x2ea61b,0.65),
       new LXFloat4(0x478966,0.70),
       new LXFloat4(0x000000,1.00)
    };

    this.darkLight = new Gradient(darkLight, Gradient.ColorMode.RGB);
}

  Umbrella(int id, String ip, Boolean reversed, int inner_leds, int empty_leds, int outer_leds, double inner_radius, double outer_radius, double xl, double yl, double zl) {
    super(ip);

    initGradients();

    List<LXPoint> leds = new ArrayList<LXPoint>();
    
    if (reversed) {
      for (int p = 0; p < outer_leds; p++) {
        double xm = Math.sin((2.0 * Math.PI / (double)outer_leds) * (outer_leds - p - 1));
        double ym = Math.cos((2.0 * Math.PI / (double)outer_leds) * (outer_leds - p - 1));
        double x = xm * outer_radius; 
        double y = ym * outer_radius; 
        LXPoint pb = new LXPoint(x-xl,y+yl,zl);
        addPoint(pb);
        leds.add(pb);
      }

      for (int p = 0; p < inner_leds; p++) {
        double xm = Math.sin((2.0 * Math.PI / (double)inner_leds) * p);
        double ym = Math.cos((2.0 * Math.PI / (double)inner_leds) * p);
        double x = xm * inner_radius; 
        double y = ym * inner_radius; 
        LXPoint pb = new LXPoint(x-xl,y+yl,zl);
        addPoint(pb);
        leds.add(pb);
      }
      
    } else {
      for (int p = 0; p < inner_leds; p++) {
        double xm = Math.sin((2.0 * Math.PI / (double)inner_leds) * p);
        double ym = Math.cos((2.0 * Math.PI / (double)inner_leds) * p);
        double x = xm * inner_radius; 
        double y = ym * inner_radius; 
        LXPoint pb = new LXPoint(x-xl,y+yl,zl);
        addPoint(pb);
        leds.add(pb);
      }
      
      for (int p = 0; p < outer_leds; p++) {
        double xm = Math.sin((2.0 * Math.PI / (double)outer_leds) * (outer_leds - p - 1));
        double ym = Math.cos((2.0 * Math.PI / (double)outer_leds) * (outer_leds - p - 1));
        double x = xm * outer_radius; 
        double y = ym * outer_radius; 
        LXPoint pb = new LXPoint(x-xl,y+yl,zl);
        addPoint(pb);
        leds.add(pb);
      }
    }
    
    this.id = id;
    this.leds = Collections.unmodifiableList(leds);
  }

  public final int id;
};
