package net.scatteredbits;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import se.uu.ucr.livingdoc.annotationhelp.ClassFinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LivingDocTest {

    private List<Class> clazzes;

    @Before
    public void setup() throws IOException, ClassNotFoundException {
        clazzes = ClassFinder.getClasses("se.uu.ucr.livingdoc");
    }


    @Test
    public void createSpecificationFile() throws JsonProcessingException {

        Class specificationClass = Specification.class;

        List<Class> specClasses= clazzes.stream()
                .filter(c -> c.isAnnotationPresent(specificationClass))
                .collect(Collectors.toList());


        List<SpecificationWithExamples> specs =new ArrayList<>();
        specClasses.stream()
                .map(sc -> specs.add(toSpec(sc))).collect(Collectors.toList());


        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(specs);
        System.out.println();
        System.out.println("------  Documentation från tester -> json -> 'rapport' _-----");
        System.out.println("Klipp in följande json i textarean på http://json.bloople.net för att få en gräsligt ful html-rapport:");
        System.out.println(json);

    }

    private SpecificationWithExamples toSpec(Class classWithSpec) {
        String specName = ((Specification)classWithSpec.getAnnotation(Specification.class)).name();
        String specDesc = ((Specification)classWithSpec.getAnnotation(Specification.class)).description();


        List<String> keyExampleDescriptions =
                Arrays.asList(classWithSpec.getMethods()).stream()
                        .flatMap(m -> Arrays.asList(m.getAnnotations()).stream()
                        .filter(a -> a.annotationType().getSimpleName().equals("KeyExample")))
                        .map(a -> ((KeyExample)a).description())
                        .collect(Collectors.toList());

        return new SpecificationWithExamples(specName, specDesc, keyExampleDescriptions);
    }


}
