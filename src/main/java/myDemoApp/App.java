package myDemoApp;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

public class App {

    public static boolean search(ArrayList<Integer> array, int e) {
        System.out.println("inside search");
        if (array == null)
            return false;

        for (int elt : array) {
            if (elt == e)
                return true;
        }
        return false;
    }

    public static double quarterlyAverage(ArrayList<Double> q1, ArrayList<Double> q2, ArrayList<Double> q3,
            ArrayList<Double> q4) {
        if (q1 == null || q2 == null || q3 == null || q4 == null) {
            return -1;
        }
        if (q1.isEmpty() || q2.isEmpty() || q3.isEmpty() || q4.isEmpty()) {
            return -1;
        }
        double average = 0;
        for (int i = 0; i < q1.size(); i++) {
            average += q1.get(i);
        }
        for (int i = 0; i < q2.size(); i++) {
            average += q2.get(i);
        }
        for (int i = 0; i < q3.size(); i++) {
            average += q3.get(i);
        }
        for (int i = 0; i < q4.size(); i++) {
            average += q4.get(i);
        }
        if (average < 0) {
            return -1;
        }
        return average / 4;
    }

    public static void main(String[] args) {
        port(getHerokuAssignedPort());

        get("/", (req,
                res) -> "Welcome to Quarterly Income Analyzer. To analyze your income please add /compute below in your browser.");

        post("/compute", (req, res) -> {

            // Quarter1
            String input1 = req.queryParams("input1");
            java.util.Scanner sc1 = new java.util.Scanner(input1);
            sc1.useDelimiter("[;\r\n]+");
            java.util.ArrayList<Double> q1 = new java.util.ArrayList<>();
            while (sc1.hasNext()) {
                double value = Double.parseDouble(sc1.next().replaceAll("\\s", ""));
                q1.add(value);
            }
            sc1.close();
            System.out.println(q1);

            // Quarter2

            String input2 = req.queryParams("input2");
            java.util.Scanner sc2 = new java.util.Scanner(input2);
            sc2.useDelimiter("[;\r\n]+");
            java.util.ArrayList<Double> q2 = new java.util.ArrayList<>();
            while (sc2.hasNext()) {
                double value = Double.parseDouble(sc2.next().replaceAll("\\s", ""));
                q2.add(value);
            }
            sc2.close();
            System.out.println(q2);
            // Quarter3

            String input3 = req.queryParams("input3");
            java.util.Scanner sc3 = new java.util.Scanner(input3);
            sc3.useDelimiter("[;\r\n]+");
            java.util.ArrayList<Double> q3 = new java.util.ArrayList<>();
            while (sc3.hasNext()) {
                double value = Double.parseDouble(sc3.next().replaceAll("\\s", ""));
                q3.add(value);
            }
            sc3.close();
            System.out.println(q3);

            // Quarter4
            String input4 = req.queryParams("input4");
            java.util.Scanner sc4 = new java.util.Scanner(input4);
            sc4.useDelimiter("[;\r\n]+");
            java.util.ArrayList<Double> q4 = new java.util.ArrayList<>();
            while (sc4.hasNext()) {
                double value = Double.parseDouble(sc4.next().replaceAll("\\s", ""));
                q4.add(value);
            }
            sc4.close();
            System.out.println(q4);

            double result = App.quarterlyAverage(q1, q2, q3, q4);
            String str_result = "";
            if (result < 0) {
                str_result = "Input is wrong, try one more time";
            } else {
                str_result = String.valueOf(result);
            }

            Map<String, String> map = new HashMap<String, String>();
            map.put("result", str_result);
            return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());

        get("/compute", (rq, rs) -> {
            Map<String, String> map = new HashMap<String, String>();
            map.put("result", "not computed yet!");
            return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; // return default port if heroku-port isn't set (i.e. on localhost)
    }
}
