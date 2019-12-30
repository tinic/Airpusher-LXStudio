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

import heronarts.lx.LX;
import heronarts.lx.model.LXModel;

import org.tinic.airpusher.Airpusher;
import org.tinic.airpusher.AirpusherPattern;
import org.tinic.airpusher.NetworkSetup;

import java.io.File;

public class AirpusherHeadless {
  public static void main(String[] args) {
    try {
      LXModel model = Airpusher.create();
      LX lx = new LX(model);
      lx.registerPattern(AirpusherPattern.class);

      NetworkSetup.setup(lx);

      if (args.length > 0) {
        lx.openProject(new File(args[0]));
      }

      lx.engine.start();
    } catch (Exception x) {
      System.err.println(x.getLocalizedMessage());
    }
  }
}
