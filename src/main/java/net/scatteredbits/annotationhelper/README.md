## Living Documentation

### Intern dokumentation av koden, i koden, för kodare
* Javadoc ...
* Gör egna informativa annoteringar för kodmönster och liknande:

![annoteringar](annotation.JPG)

Förutom att de enskilda annoteringarna kan ge hjälp under kodandet, kan
 man också lätt söka efter dem i koden och på så sätt se hur koden är uppbyggd:
 
 
![hitta_annoteringar](findbyannotation.JPG)
 

Andra intressanta kodbitar att annotera kan vara

* exempelkod som ska fungera som förebild för hur problem ska lösas "@Example"
* intressant kod som är värdefull för andra att titta på @PointOfInterest
    

### Extern dokumentation av koden, från koden, för kodare
Man kan naturligtvis också använda annoterad kod för att skapa extern dokumentation:

* Skriv intern eller extern kod som letar upp vissa annoteringar. Det går enkelt att skriva sådan kod direkt i Java 
eller använda ett extert, gratis kodbibliotek som till exempel Qdox

* När man letat upp annoteringarna kan man själv hitta på något visuellt kreativt att göra med dem, eller så kan man använda 
begfintliga externa tjänster även för det, till exempel http://yuml.me/diagram/class/draw för att göra små diagram

#### Liten test av Yuml
Jag har en annotering @EffectivelyImmutable med en kort javadoc-beskrivning av vad annoteringen innebär
 samt några klasser som är annoterade med annoteringen. Med lite egen [kod](livingdoc-demo/src/test/java/se/uu/ucr/livingdoc/subjectmanagement/LivingDocTest.java)
   letar jag upp annoteringen och de annoterade klasserna och skapar en följande sträng: 
 
<pre>
[EffectivelyImmutable]-[note:  Classes marked as EffectivelyImmutable should be immutable from the standpoint of the programmer. 
However they may actually be updated as part of creation by JPA or other framework that insists to work that way {bg:cornsilk}],

[EffectivelyImmutable]^-[ScreeningLogEntry], [EffectivelyImmutable]^-[SubjectLogEntry]
</pre>

... så kan jag skicka denna sträng som ett anrop till Yuml och få tillbaka en bild:

<pre>
http://yuml.me/diagram/plain;dir:LR/class/[EffectivelyImmutable]-[note:%20Classes%20marked%20as%20EffectivelyImmutable%20should%20be%20immutable%20from%20the%20standpoint%20of%20the%20programmer.%20However%20they%20may%20actually%20be%20updated%20as%20part%20of%20creation%20by%20JPA%20or%20other%20framework%20that%20insists%20to%20work%20that%20way%20%7Bbg:cornsilk%7D],[EffectivelyImmutable]%5E-[ScreeningLogEntry],%20[EffectivelyImmutable]%5E-[SubjectLogEntry]
</pre>

![YUML](http://yuml.me/diagram/plain;dir:LR/class/[EffectivelyImmutable]-[note:%20Classes%20marked%20as%20EffectivelyImmutable%20should%20be%20immutable%20from%20the%20standpoint%20of%20the%20programmer.%20However%20they%20may%20actually%20be%20updated%20as%20part%20of%20creation%20by%20JPA%20or%20other%20framework%20that%20insists%20to%20work%20that%20way%20%7Bbg:cornsilk%7D],[EffectivelyImmutable]%5E-[ScreeningLogEntry],%20[EffectivelyImmutable]%5E-[SubjectLogEntry])

* Det fina är alltså nu att om jag lägger till en klass som är annoterad med @EffectivelyImmutable, samt kör min kod så kommer jag att få en uppdaterad bild.

### Extern dokumentation av koden, från koden, för alla
* Genom att annotera vanliga enhetstester kan man lätt göra en sorts fattigmans-Cucumber/JBehave/.. 
* Annoterar tester med  dokumentation av de (affärs-)krav som testet ska kontrollera.
* Kör testerna för att kolla att testerna/affärreglerna är uppfyllda
* Kör sedan lite kod som extraherar dokumentationen från testerna. Om man vill kan man göra det som en del av testandet i förra punkten
* Den extraherade dokumentationen (i till exempel json) används för att skapa en rapport.

Ett exempel på en vanlig testklass med med en annotering på klassen som övergripande berättar vad som ska testas:

<pre>
@Specification(
        name="Klarmarkera en registrering",
        description =
                "För att veta när en registrering kan anses vara färdig, använder vi begreppet 'klarmarkerad'." +
                "Det är först när en registrering är klarmarkerad som det är lämpligt att använda registreringens data i rapporter. " +
                "En registrering går alltid att spara, men bara att klarmarkera om alla obligatoriska fält är ifyllda." +
                "När registreringen är klarmarkerad kan inte användaren redigera den, men användaren kan alltid " +
                "låsa upp en klarmarkerad registrering och därefter redigera.")
public class SpecifictionTest {
    ...
    ...
}
</pre>

... och i klassen annoterar man sedan testerna med mer exakta beskrivningar

<pre>
    @Test
    @KeyExample(description = "En användare kan alltid spara en icke klarmarkerad registrering")
    public void asdasdasd() {
        // kod för att testa det som står i @KeyExample
        assertTrue(true);
    }

    @Test
    @KeyExample(description = "En användare kan inte klarmarkera en registrering om inte alla obligatoriska fält är ifyllda")
    public void asdfsdf() {
        // kod för att testa det som står i @KeyExample
        assertTrue(true);
    }
</pre>

Med några rader kod kan man extrahera fram texterna till en json-sträng:

<pre>
[{
"specName":"Klarmarkera en registrering",

"specDesc":"För att veta när en registrering kan anses vara färdig, använder vi begreppet 'klarmarkerad'.Det är först när en registrering är klarmarkerad som det är lämpligt att använda registreringens data i rapporter. En registrering går alltid att spara, men bara att klarmarkera om alla obligatoriska fält är ifyllda.När registreringen är klarmarkerad kan inte användaren redigera den, men användaren kan alltid låsa upp en klarmarkerad registrering och därefter redigera.",

"keyExampleDescriptions":
	[
	"En användare kan alltid spara en icke klarmarkerad registrering",
	"En användare kan inte klarmarkera en registrering om inte alla obligatoriska fält är ifyllda",
	"En användare kan alltid klarmarkera en registrering om alla obligatoriska fält är ifyllda",
	"En användare kan alltid låsa upp en klarmarkerad registrering"
	]
}]
</pre>

... Och denna Json-sträng kan sedan användas för att göra snygg dokumentation.