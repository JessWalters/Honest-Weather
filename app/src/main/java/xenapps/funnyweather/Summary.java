package xenapps.funnyweather;

import java.util.Random;

/**
 * Created by sable_000 on 2/23/2015.
 */
public class Summary {

    private static String[] hotDry = {
        "It's hot as fuck!",
        "It's hotter than the fucking sun!",
        "Hotter than all hell out there!",
        "I'm going to be honest, It's fucking hot!",
        "Hotter Than Hell's Kitchen!",
        "It's hotter than Megan Fox!",
        "Holy fuck, it's hot!"
    };

    private static String[] coldDry = {
            "Colder than your ex's heart!",
            "Fucking freezing!",
            "Stay in bed, it's cold as fuck!",
            "Colder than a witch's tit!"
    };

    private static String[] rainy = {
            "It's fucking wet out!",
            "It's fucking raining!",
            "Tons of fucking rain!"
    };

    private static String[] snowy = {
            "God damned Winter Wonderland!",
            "It's fucking snowing!",
            "Hell has frozen over!"
    };

    private static String[] midTemps = {
            "It's yoga pants season!",
            "It's fucking beautiful out",
            "It's too good to let this weather go to waste!",
            "Holy fuck, it's not dreadful out"
    };

    private static String[] tStorm = {
            "Fuck you thunder!",
            "Fucking lightning and shit!",
            "Thunder and lightning!"

    };

    private static String[] foggy = {
            "Foggy... like Stevie wonder in a maze",
            "Foggy as fuck!",
            "Can't see shit!"
    };

    private static String[] windy = {
            "It's blowing like a taiwanese hooker",
            "Fucking windy as shit",
            "The air is like... Moving!"
    };

    private static String[] hurricane = {
            "It's Katrina all over again!",
            "Holy fuck!! A Hurricane"
    };

    private static String[] tornado = {
            "Get the fuck inside!",
            "Holy fuck a tornado!"
    };

    private static String[] coldWet = {
            "Cold and damp, like senior citizen pron",
            "Meh.. Stay inside",
            "It's really fucking shitty outside!",
            "Wet and cold...",
            "This weather is just terrible"
    };

    private static String[] volcanoAsh = {
            "It's raining fucking fire!! Fuck!"
    };

    private static String[] hail = {
            "Why the fuck is it raining ICE!!!",
            "Hope to god your fucking car is inside!"
    };

    public static String[] getType(int condition, double temp) {
        String[] summaryArray = null;

        if (condition == 800 || condition == 801 || condition == 951 || condition == 904 || condition == 804 || condition == 903 || condition == 803 || condition == 802 || condition == 952 || condition == 953 || condition == 954 || condition == 955 ) {
            if (temp > 80) {
                summaryArray = hotDry;
            }

            else if (temp < 40) {
                summaryArray = coldDry;
            }

            else {
                summaryArray = midTemps;
            }

        }

        else if (condition == 300 || condition == 301 || condition == 302 || condition == 310 || condition == 311 || condition == 312 || condition == 313 || condition == 314 || condition == 321 || condition == 502 || condition == 503 || condition == 504 || condition == 522 || condition == 500 || condition == 501 || condition == 520 || condition == 521 || condition == 531) {
            if(temp < 40) {
                summaryArray = coldWet;
            }
            else {
                summaryArray = rainy;
            }
        }

        else if (condition == 602 || condition == 622 || condition == 600 || condition == 620 || condition == 601 || condition == 621 || condition == 615 || condition == 616) {
            summaryArray = snowy;
        }

        else if (condition == 200 || condition == 201 || condition == 202 || condition == 210 || condition == 211 || condition == 221 || condition == 212 || condition == 230 || condition == 231 || condition == 232) {
            summaryArray = tStorm;
        }

        else if (condition== 701 || condition == 711 || condition == 721 || condition == 731 || condition == 741 || condition == 751 || condition == 761 || condition == 771) {
            summaryArray = foggy;
        }

        else if (condition == 905 ||  condition == 956 || condition == 957 || condition == 958 || condition == 959 || condition == 960 || condition == 961) {
            summaryArray = windy;
        }

        else if (condition == 901 || condition == 902 || condition == 962) {
            summaryArray = hurricane;
        }

        else if (condition == 900 || condition == 781) {
            summaryArray = tornado;
        }

        else if (condition == 511 || condition == 611 || condition == 612) {
            summaryArray = coldWet;
        }

        else if (condition == 762) {
            summaryArray = volcanoAsh;
        }

        else if (condition == 906) {
            summaryArray = hail;
        }
        else {
            summaryArray = midTemps;
        }


        return summaryArray;
    }

    public static String getSummary(String[] summaryArray) {
        Random random = new Random();
        String summary = summaryArray[random.nextInt(summaryArray.length)];
        return summary;
    }
}
