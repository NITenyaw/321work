package eu.q5x.a321work;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import us.feras.mdv.MarkdownView;

public class Impressum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impressum);

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("321Work!");
        ab.setSubtitle("Impressum");

        MarkdownView impressum_text = (MarkdownView) findViewById(R.id.impressum_text);
        impressum_text.loadMarkdown("**321work!** was developed by the **321Hack!** team during the **Hackathon 2016** in Freiburg (Topic: Newcomer).\n" +
                "\n" +
                "Team members:\n" +
                "\n" +
                "**Lucas Spohn, Jérôme Meinke, Benedikt Throner, Jan Vogt, Marcel Gangwisch, Patrick Bastien und Semih Volkert** \n" +
                "\n");


        MarkdownView license = (MarkdownView) findViewById(R.id.license);
        license.loadMarkdown("The MIT License (MIT)\n" +
                "\n" +
                "Copyright (c) 2016 Lucas Spohn, Jérôme Meinke, Benedikt Throner, Jan Vogt, Marcel Gangwisch, Patrick Bastien und Semih Volkert\n" +
                "\n" +
                "Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the \"Software\"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:\n" +
                "\n" +
                "The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.\n" +
                "\n" +
                "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.");
    }
}
