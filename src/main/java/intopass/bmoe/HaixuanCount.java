package intopass.bmoe;

import intopass.bmoe.spdier.Person;
import intopass.bmoe.spdier.Spider;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

/**
 * Created by fuhaiwei on 15/12/23.
 */
public class HaixuanCount {
    public static void main(String[] args) {
        List<Person> haixuan = Spider.get_persons(LocalDate.of(2015, 10, 31), LocalDate.of(2015, 12, 6));
        List<Person> benzhan_32 = Spider.get_persons(LocalDate.of(2015, 12, 20), LocalDate.of(2015, 12, 24));

        List<Person> haixuan_32 = benzhan_32.stream()
                .map(p -> find_by_rid(haixuan, p.rid))
                .collect(toList());

        print_date("海选晋级32强日期分布情况", haixuan_32);
    }

    private static void print_date(String title, List<Person> persons) {
        System.out.println(title);
        Map<String, List<Person>> collect = persons.stream()
                .filter(p -> p.sex == 0)
                .collect(groupingBy(p -> p.date));
        collect.keySet().stream()
                .sorted()
                .forEach(date -> {
                    List<Person> dates = collect.get(date);
                    String names = dates.stream()
                            .sorted()
                            .map(p -> p.name + "(" + p.rank + ")")
                            .collect(joining(" "));
                    System.out.printf("%s: %d人 %s%n",
                            date.substring(5), dates.size(), names);
                });
    }

    private static Person find_by_rid(List<Person> haixuan, int rid) {
        return haixuan.stream().filter(p -> p.rid == rid).findFirst().get();
    }
}
