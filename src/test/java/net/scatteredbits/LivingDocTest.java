package net.scatteredbits;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import se.uu.ucr.livingdoc.annotationhelp.ClassFinder;
import se.uu.ucr.livingdoc.annotations.Documentation;
import se.uu.ucr.livingdoc.annotations.EffectivelyImmutable;

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
    public void createUrlForEffectivelyImmutableImage() throws IOException, ClassNotFoundException {
        Class effectivelyImmutableClass = EffectivelyImmutable.class;
        String documentationText = ((Documentation)effectivelyImmutableClass.getAnnotation(Documentation.class)).value();


        List<String> classNames = clazzes.stream()
                .filter(c -> c.isAnnotationPresent(effectivelyImmutableClass))
                .map(c -> c.getSimpleName())
                .collect(Collectors.toList());


        String notePart = "[EffectivelyImmutable]-[note:"+ documentationText +"]";
        String classes = classNames.stream().map(cn -> ",[EffectivelyImmutable]-[" + cn +"]").collect(Collectors.joining());

        System.out.println();
        System.out.println("------  Annoteringar -> bild' ------");
        System.out.println("Det här anropet kommer att producera en bild som visar alla våra immutable-klasser:");
        System.out.println("http://yuml.me/diagram/plain;dir:LR/class/" + notePart + classes);
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
