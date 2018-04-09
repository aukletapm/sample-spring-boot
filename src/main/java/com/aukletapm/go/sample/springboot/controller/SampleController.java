/*
 * Copyright [2018] [Eric Xu]
 *
 *     Author: Eric Xu
 *     Email: eric.xu@aukletapm.com
 *     WebURL: https://github.com/aukletapm
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aukletapm.go.sample.springboot.controller;

import com.aukletapm.go.AukletApmToGo;
import com.aukletapm.go.LineChart;
import com.aukletapm.go.module.JvmModule;
import com.aukletapm.go.module.OsModule;
import com.aukletapm.go.servlet.AukletApmToGoHttpServletHandler;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

/**
 * @author Eric Xu
 * @date 01/03/2018
 */
@Controller
public class SampleController {

    private AukletApmToGoHttpServletHandler aukletApmToGoHttpServletHandler;

    @PostConstruct
    public void init() {
        aukletApmToGoHttpServletHandler = AukletApmToGoHttpServletHandler.newBuilder()
                .name("My Site")
                .addModule(new OsModule())
                .addModule(new JvmModule())
                .build();


        aukletApmToGoHttpServletHandler
                .getService()
                .indexPage()

                //create a customized line chart
                .startLineChart("line_chart")
                .description("Line Chart Sample")
                .loadData(() -> {
                    Random random = new Random();
                    return Lists.newArrayList(
                            new LineChart.LoadData("Line1", random.nextDouble()),
                            new LineChart.LoadData("Line2", random.nextDouble()),
                            new LineChart.LoadData("Line3", random.nextDouble())
                    );
                })
                .endLineChart()

                //create a customized line chart
                .startPieChart("pie_chart", "Pie Chart Sample")
                .setContentLoader(o -> {
                    Random random = new Random();
                    return new AukletApmToGo.PieChartData.Builder()
                            .data("2017", "Java", random.nextDouble())
                            .data("2017", "Kotlin", random.nextDouble())
                            .data("2017", "Ruby", random.nextDouble())
                            .data("2017", "Javascript", random.nextDouble())
                            .data("2018", "Java", random.nextDouble())
                            .data("2018", "Kotlin", random.nextDouble())
                            .data("2018", "Ruby", random.nextDouble())
                            .data("2018", "Javascript", random.nextDouble())
                            .build();
                })
                .endPieChart()

                //create a customized data list
                .startList("list_sample", "Data List Sample")
                .setContentLoader(o -> Lists.newArrayList(
                        new AukletApmToGo.KeyValue("Name", "AukletAPM To Go"),
                        new AukletApmToGo.KeyValue("Platform", "Android"),
                        new AukletApmToGo.KeyValue("Price", "Free")
                ))

                .endList();


    }

    @CrossOrigin(origins = {"*"})
    @RequestMapping("/aukletapm-to-go")
    public void akuletGoEndpoint(HttpServletRequest request, HttpServletResponse response) {
        aukletApmToGoHttpServletHandler.handle(request, response);
    }

    @RequestMapping("/")
    public String home() {
        return "redirect:/aukletapm-to-go";
    }

}
