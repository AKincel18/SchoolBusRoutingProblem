package algorithm;

import distance.BusDistance;
import distance.SchoolDistance;
import model.Bus;
import model.School;

import java.util.*;
import java.util.stream.Collectors;

/**
 * dependency analysis between buses capacity and number of pupils belonging to school
 */
public class DependencyAnalysis {

    private static Map<Bus, List<School>> analysisMap = new HashMap<>();

    static Map<Bus, List<School>> analysis(Map<Bus, List<BusDistance>> distanceBetweenBusesAndPupils,
                          Map<School, List<SchoolDistance>> distanceBetweenSchoolsAndPupils) {
        for (Map.Entry<Bus, List<BusDistance>> mapBus: distanceBetweenBusesAndPupils.entrySet()) {

            List<School> schools = new ArrayList<>();
            for (Map.Entry<School, List<SchoolDistance>> mapSchool: distanceBetweenSchoolsAndPupils.entrySet()) {
                if (mapBus.getKey().getCapacity() >= (mapSchool.getValue().size()) ) {
                    schools.add(mapSchool.getKey());
                }
            }
            analysisMap.put(mapBus.getKey(), schools);
        }

        sortedAnalysisBySizeOfSchools();
        return analysisMap;
    }

    private static void sortedAnalysisBySizeOfSchools() {
        analysisMap = analysisMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparing(List::size)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
