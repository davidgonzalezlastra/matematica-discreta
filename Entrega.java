import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/*
 * Aquesta entrega consisteix en implementar tots els mètodes annotats amb "// TODO". L'enunciat de
 * cada un d'ells és al comentari de la seva signatura i exemples del seu funcionament als mètodes
 * `Tema1.tests`, `Tema2.tests`, etc.
 *
 * L'avaluació consistirà en:
 *
 * - Si el codi no compila, la nota del grup serà un 0.
 *
 * - Si heu fet cap modificació que no sigui afegir un mètode, afegir proves als mètodes "tests()" o
 *   implementar els mètodes annotats amb "// TODO", la nota del grup serà un 0.
 *
 * - Principalment, la nota dependrà del correcte funcionament dels mètodes implemnetats (provant
 *   amb diferents entrades).
 *
 * - Tendrem en compte la neteja i organització del codi. Un estandard que podeu seguir és la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html . Algunes
 *   consideracions importants:
 *    + Entregau amb la mateixa codificació (UTF-8) i finals de línia (LF, no CR+LF)
 *    + Indentació i espaiat consistent
 *    + Bona nomenclatura de variables
 *    + Declarar les variables el més aprop possible al primer ús (és a dir, evitau blocs de
 *      declaracions).
 *    + Convé utilitzar el for-each (for (int x : ...)) enlloc del clàssic (for (int i = 0; ...))
 *      sempre que no necessiteu l'índex del recorregut.
 *
 * Per com està plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni qualificar classes que no estiguin ja importades. El que sí podeu fer és definir
 * tots els mètodes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a màxim 3 persones, i necessitareu com a minim Java 10.
 * Per entregar, posau a continuació els vostres noms i entregau únicament aquest fitxer.
 * - Nom 1:
 * - Nom 2:
 * - Nom 3:
 *
 * L'entrega es farà a través d'una tasca a l'Aula Digital que obrirem abans de la data que se us
 * hagui comunicat i vos recomanam que treballeu amb un fork d'aquest repositori per seguir més
 * fàcilment les actualitzacions amb enunciats nous. Si no podeu visualitzar bé algun enunciat,
 * assegurau-vos de que el vostre editor de texte estigui configurat amb codificació UTF-8.
 */
class Entrega {
  /*
   * Aquí teniu els exercicis del Tema 1 (Lògica).
   *
   * La majoria dels mètodes reben de paràmetre l'univers (representat com un array) i els predicats
   * adients (per exemple, `Predicate<Integer> p`). Per avaluar aquest predicat, si `x` és un
   * element de l'univers, podeu fer-ho com `p.test(x)`, que té com resultat un booleà (true si
   * `P(x)` és cert). Els predicats de dues variables són de tipus `BiPredicate<Integer, Integer>` i
   * similarment s'avaluen com `p.test(x, y)`.
   *
   * En cada un d'aquests exercicis (excepte el primer) us demanam que donat l'univers i els
   * predicats retorneu `true` o `false` segons si la proposició donada és certa (suposau que
   * l'univers és suficientment petit com per poder provar tots els casos que faci falta).
   */
  static class Tema1 {
    /*
     * Donat n > 1, en quants de casos (segons els valors de veritat de les proposicions p1,...,pn)
     * la proposició (...((p1 -> p2) -> p3) -> ...) -> pn és certa?
     *
     * Vegeu el mètode Tema1.tests() per exemples.
     */

     /**Declaro contador con valor 0
      * totalCombinacions: Comprobamos el número infinito de posibles combinaciones
      *Genero un bucle donde se realiza el buscado de la combinación 
      Genero un método evaluar evaluateProposicions donde una vez obtengo la proposición verifico  
      que la implicación que se plantea, sea cierta. 

      */
    static int exercici1(int n) {
      int count = 0;
      int totalCombinations = (int) Math.pow(2, n); // 2^n combinations
      
      for (int i = 0; i < totalCombinations; i++) {
          boolean[] values = new boolean[n];
          for (int j = 0; j < n; j++) {
              values[j] = (i & (1 << j)) != 0; // Generate boolean values for p1, p2, ..., pn
          }
          if (evaluateProposition(values)) {
              count++;
          }
      }
      return count;
    }  

  static boolean evaluateProposition(boolean[] values) {
      int n = values.length;
      boolean result = values[0]; // Start with p1
      for (int i = 1; i < n; i++) {
          result = !result || values[i]; // Evaluate (p1 -> p2) -> p3 -> ... -> pn
      }
      return result;
       // TODO
  }

      
    

    /*
     * És cert que ∀x : P(x) -> ∃!y : Q(x,y) ?
     */
    static boolean exercici2(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
     
      for (int x : universe) {
        if (p.test(x)) { // Check if P(x) is true
            int count = 0;
            for (int y : universe) {
                if (q.test(x, y)) {
                    count++;
                }
            }
            if (count != 1) { // There should be exactly one y for which Q(x, y) is true
                return false;
            }
        }
    }
    return true; 
  }
     
    /*
     * És cert que ∃x : ∀y : Q(x, y) -> P(x) ?
     */
    static boolean exercici3(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
      
      for (int x : universe) {
        boolean allY = true;
        for (int y : universe) {
            if (q.test(x, y) && !p.test(x)) {
                allY = false;
                break;
            }
        }
        if (allY) {
            return true;
        }
    }
      
      
      return false; // TODO
    }

    /*
     * És cert que ∃x : ∃!y : ∀z : P(x,z) <-> Q(y,z) ?
     */
    static boolean exercici4(int[] universe, BiPredicate<Integer, Integer> p, BiPredicate<Integer, Integer> q) {
      
      for (int x : universe) {
        int uniqueYCount = 0;
        for (int y : universe) {
            boolean allZ = true;
            for (int z : universe) {
                if (p.test(x, z) != q.test(y, z)) {
                    allZ = false;
                    break;
                }
            }
            if (allZ) {
                uniqueYCount++;
            }
        }
        if (uniqueYCount == 1) {
            return true;
        }
    }
    
      return false; // TODO
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1

      // p1 -> p2 és cert exactament a 3 files
      // p1 p2
      // 0  0  <-
      // 0  1  <-
      // 1  0
      // 1  1  <-
      assertThat(exercici1(2) == 3);

      // (p1 -> p2) -> p3 és cert exactament a 5 files
      // p1 p2 p3
      // 0  0  0
      // 0  0  1  <-
      // 0  1  0
      // 0  1  1  <-
      // 1  0  0  <-
      // 1  0  1  <-
      // 1  1  0
      // 1  1  1  <-
      assertThat(exercici1(3) == 5);

      // Exercici 2
      // ∀x : P(x) -> ∃!y : Q(x,y)
      assertThat(
          exercici2(
            new int[] { 1, 2, 3 },
            x -> x % 2 == 0,
            (x, y) -> x+y >= 5
          )
      );

      assertThat(
          !exercici2(
            new int[] { 1, 2, 3 },
            x -> x < 3,
            (x, y) -> x-y > 0
          )
      );

      // Exercici 3
      // És cert que ∃x : ∀y : Q(x, y) -> P(x) ?
      assertThat(
          exercici3(
            new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
            x -> x % 3 != 0,
            (x, y) -> y % x == 0
          )
      );

      assertThat(
          exercici3(
            new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
            x -> x % 4 != 0,
            (x, y) -> (x*y) % 4 != 0
          )
      );

      // Exercici 4
      // És cert que ∃x : ∃!y : ∀z : P(x,z) <-> Q(y,z) ?
      assertThat(
          exercici4(
            new int[] { 0, 1, 2, 3, 4, 5 },
            (x, z) -> x*z == 1,
            (y, z) -> y*z == 2
          )
      );

      assertThat(
          !exercici4(
            new int[] { 2, 3, 4, 5 },
            (x, z) -> x*z == 1,
            (y, z) -> y*z == 2
          )
      );
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 2 (Conjunts).
   *
   * Per senzillesa tractarem els conjunts com arrays (sense elements repetits). Per tant, un
   * conjunt de conjunts d'enters tendrà tipus int[][].
   *
   * Les relacions també les representarem com arrays de dues dimensions, on la segona dimensió
   * només té dos elements. Per exemple
   *   int[][] rel = {{0,0}, {0,1}, {1,1}, {2,2}};
   * i també donarem el conjunt on està definida, per exemple
   *   int[] a = {0,1,2};
   * Als tests utilitzarem extensivament la funció generateRel definida al final (també la podeu
   * utilitzar si la necessitau).
   *
   * Les funcions f : A -> B (on A i B son subconjunts dels enters) les representam o bé amb el seu
   * graf o amb un objecte de tipus Function<Integer, Integer>. Sempre donarem el domini int[] a, el
   * codomini int[] b. En el cas de tenir un objecte de tipus Function<Integer, Integer>, per aplicar
   * f a x, és a dir, "f(x)" on x és d'A i el resultat f.apply(x) és de B, s'escriu f.apply(x).
   */
  static class Tema2 {
    /*
     * Calculau el nombre d'elements del conjunt de parts de (a u b) × (a \ c)
     *
     * Podeu soposar que `a`, `b` i `c` estan ordenats de menor a major.
     */
      static int exercici1(int[] a, int[] b, int[] c) {
        // Trobar A ∪ B
        Set<Integer> union = new HashSet<>();
        for (int elem : a) union.add(elem);
        for (int elem : b) union.add(elem);

        // Trobar A \ C
        Set<Integer> difference = new HashSet<>();
        for (int elem : a) difference.add(elem);
        for (int elem : c) difference.remove(elem);

        // Producte cartesià (A ∪ B) × (A \ C)
        int sizeUnion = union.size();
        int sizeDifference = difference.size();
        int cartesianProductSize = sizeUnion * sizeDifference;

        // Nombre d'elements del conjunt de parts
        int result = (int) Math.pow(2, cartesianProductSize);


      return -1; // TODO
    }

    /*
     * La clausura d'equivalència d'una relació és el resultat de fer-hi la clausura reflexiva, simètrica i
     * transitiva simultàniament, i, per tant, sempre és una relació d'equivalència.
     *
     * Trobau el cardinal d'aquesta clausura.
     *
     * Podeu soposar que `a` i `rel` estan ordenats de menor a major (`rel` lexicogràficament).
     */
    static int exercici2(int[] a, int[][] rel) {
      int n = a.length;

        // Pas 1: Clausura reflexiva
        Set<int[]> closure = new HashSet<>();
        for (int x : a) {
            closure.add(new int[]{x, x});
        }

        // Afegim els parells originals
        for (int[] pair : rel) {
            closure.add(pair);
        }

        // Pas 2: Clausura simètrica
        Set<int[]> toAdd = new HashSet<>();
        for (int[] pair : closure) {
            toAdd.add(new int[]{pair[1], pair[0]});
        }
        closure.addAll(toAdd);

        // Pas 3: Clausura transitiva
        boolean added;
        do {
            added = false;
            Set<int[]> newPairs = new HashSet<>();
            for (int[] p1 : closure) {
                for (int[] p2 : closure) {
                    if (p1[1] == p2[0]) {
                        int[] newPair = new int[]{p1[0], p2[1]};
                        if (!containsPair(closure, newPair)) {
                            newPairs.add(newPair);
                            added = true;
                        }
                    }
                }
            }
            closure.addAll(newPairs);
        } while (added);

        // El cardinal de la clausura d'equivalència
        return closure.size();
    }

    // Mètode auxiliar per verificar si un conjunt conté un parell
    private static boolean containsPair(Set<int[]> set, int[] pair) {
        for (int[] p : set) {
            if (p[0] == pair[0] && p[1] == pair[1]) {
                return true;
            }
        }
      
      return -1; // TODO

    }

    /*
     * Comprovau si la relació `rel` és un ordre total sobre `a`. Si ho és retornau el nombre
     * d'arestes del seu diagrama de Hasse. Sino, retornau -2.
     *
     * Podeu soposar que `a` i `rel` estan ordenats de menor a major (`rel` lexicogràficament).
     */
    static int exercici3(int[] a, int[][] rel) {
      int n = a.length;

      // Convertim la relació en un conjunt per facilitar la verificació
      Set<int[]> relation = new HashSet<>();
      for (int[] pair : rel) {
          relation.add(pair);
      }

      // Verifiquem que la relació és un ordre total
      for (int x : a) {
          if (!containsPair(relation, new int[]{x, x})) {
              return -2; // No és reflexiva
          }
      }

      for (int[] p1 : rel) {
          if (p1[0] != p1[1] && containsPair(relation, new int[]{p1[1], p1[0]})) {
              return -2; // No és antisymètrica
          }
      }

      for (int[] p1 : rel) {
          for (int[] p2 : rel) {
              if (p1[1] == p2[0] && !containsPair(relation, new int[]{p1[0], p2[1]})) {
                  return -2; // No és transitiva
              }
          }
      }

      for (int x : a) {
          for (int y : a) {
              if (x != y && !containsPair(relation, new int[]{x, y}) && !containsPair(relation, new int[]{y, x})) {
                  return -2; // No és un ordre total
              }
          }
      }

      // Construim el diagrama de Hasse
      Set<int[]> hasseDiagram = new HashSet<>(relation);
      for (int[] p1 : rel) {
          if (p1[0] != p1[1]) {
              for (int[] p2 : rel) {
                  if (p1[1] == p2[0] && containsPair(relation, new int[]{p1[0], p2[1]})) {
                      hasseDiagram.remove(new int[]{p1[0], p2[1]});
                  }
              }
          }
      }

      // Retornem el nombre d'arestes del diagrama de Hasse
      return hasseDiagram.size();
  }

  // Mètode auxiliar per verificar si un conjunt conté un parell
  private static boolean containsPair(Set<int[]> set, int[] pair) {
      for (int[] p : set) {
          if (p[0] == pair[0] && p[1] == pair[1]) {
              return true;
          }
      }
      return false;
  }
      return -1; // TODO
    }


    /*
     * Comprovau si les relacions `rel1` i `rel2` són els grafs de funcions amb domini i codomini
     * `a`. Si ho son, retornau el graf de la composició `rel2 ∘ rel1`. Sino, retornau null.
     *
     * Podeu soposar que `a`, `rel1` i `rel2` estan ordenats de menor a major (les relacions,
     * lexicogràficament).
     */
    static int[][] exercici4(int[] a, int[][] rel1, int[][] rel2) {

        if (!isFunction(a, rel1)) {
            return null;
        }

        // Comprovar si rel2 és una funció
        if (!isFunction(a, rel2)) {
            return null;
        }

        // Crear mapes per a rel1 i rel2
        Map<Integer, Integer> mapRel1 = new HashMap<>();
        Map<Integer, Integer> mapRel2 = new HashMap<>();

        for (int[] pair : rel1) {
            mapRel1.put(pair[0], pair[1]);
        }

        for (int[] pair : rel2) {
            mapRel2.put(pair[0], pair[1]);
        }

        // Composar rel2 ∘ rel1
        int[][] composition = new int[a.length][2];
        int index = 0;

        for (int x : a) {
            int y = mapRel1.get(x);
            int z = mapRel2.get(y);
            composition[index][0] = x;
            composition[index][1] = z;
            index++;
        }

        return composition;
    }

    // Funció per comprovar si una relació és una funció
    private static boolean isFunction(int[] a, int[][] rel) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int[] pair : rel) {
            if (map.containsKey(pair[0])) {
                return false; // No és una funció si un element té més d'una imatge
            }
            map.put(pair[0], pair[1]);
        }
        return map.size() == a.length; // Comprovar que cada element del domini té una imatge
    }
    
      return new int[][] {}; // TODO
    }

    /*
     * Comprovau si la funció `f` amb domini `dom` i codomini `codom` té inversa. Si la té, retornau
     * el seu graf (el de l'inversa). Sino, retornau null.
     */
    static int[][] exercici5(int[] dom, int[] codom, Function<Integer, Integer> f) {
    // Verificar si f és injectiva
    for (int i = 0; i < dom.length; i++) {
      for (int j = i + 1; j < dom.length; j++) {
          if (f.apply(dom[i]) == f.apply(dom[j])) {
              return new int[][] {}; // f no és injectiva, no té inversa
          }
      }
  }

  // Verificar si f és surjectiva
  for (int y : codom) {
      boolean found = false;
      for (int x : dom) {
          if (f.apply(x) == y) {
              found = true;
              break;
          }
      }
      if (!found) {
          return new int[][] {}; // f no és surjectiva, no té inversa
      }
  }

  // f és bijectiva, té inversa
  int[][] inverseGraph = new int[dom.length][2];
  for (int i = 0; i < dom.length; i++) {
      inverseGraph[i][0] = dom[i];
      inverseGraph[i][1] = f.apply(dom[i]);
  }
  return inverseGraph;

      return new int[][] {}; // TODO
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // |(a u b) × (a \ c)|

      assertThat(
          exercici1(
            new int[] { 0, 1, 2 },
            new int[] { 1, 2, 3 },
            new int[] { 0, 3 }
          )
          == 8
      );

      assertThat(
          exercici1(
            new int[] { 0, 1 },
            new int[] { 0 },
            new int[] { 0 }
          )
          == 2
      );

      // Exercici 2
      // nombre d'elements de la clausura d'equivalència

      final int[] int08 = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

      assertThat(exercici2(int08, generateRel(int08, (x, y) -> y == x + 1)) == 81);

      final int[] int123 = { 1, 2, 3 };

      assertThat(exercici2(int123, new int[][] { {1, 3} }) == 5);

      // Exercici 3
      // Si rel és un ordre total, retornar les arestes del diagrama de Hasse

      final int[] int05 = { 0, 1, 2, 3, 4, 5 };

      assertThat(exercici3(int05, generateRel(int05, (x, y) -> x >= y)) == 5);
      assertThat(exercici3(int08, generateRel(int05, (x, y) -> x <= y)) == -2);

      // Exercici 4
      // Composició de grafs de funcions (null si no ho son)

      assertThat(
          exercici4(
            int05,
            generateRel(int05, (x, y) -> x*x == y),
            generateRel(int05, (x, y) -> x == y)
          )
          == null
      );


      var ex4test2 = exercici4(
          int05,
          generateRel(int05, (x, y) -> x + y == 5),
          generateRel(int05, (x, y) -> y == (x + 1) % 6)
        );

      assertThat(
          Arrays.deepEquals(
            lexSorted(ex4test2),
            generateRel(int05, (x, y) -> y == (5 - x + 1) % 6)
          )
      );

      // Exercici 5
      // trobar l'inversa (null si no existeix)

      assertThat(exercici5(int05, int08, x -> x + 3) == null);

      assertThat(
          Arrays.deepEquals(
            lexSorted(exercici5(int08, int08, x -> 8 - x)),
            generateRel(int08, (x, y) -> y == 8 - x)
          )
      );
    }

    /**
     * Ordena lexicogràficament un array de 2 dimensions
     * Per exemple:
     *  arr = {{1,0}, {2,2}, {0,1}}
     *  resultat = {{0,1}, {1,0}, {2,2}}
     */
    static int[][] lexSorted(int[][] arr) {
      if (arr == null)
        return null;

      var arr2 = Arrays.copyOf(arr, arr.length);
      Arrays.sort(arr2, Arrays::compare);
      return arr2;
    }

    /**
     * Genera un array int[][] amb els elements {a, b} (a de as, b de bs) que satisfàn pred.test(a, b)
     * Per exemple:
     *   as = {0, 1}
     *   bs = {0, 1, 2}
     *   pred = (a, b) -> a == b
     *   resultat = {{0,0}, {1,1}}
     */
    static int[][] generateRel(int[] as, int[] bs, BiPredicate<Integer, Integer> pred) {
      var rel = new ArrayList<int[]>();

      for (int a : as) {
        for (int b : bs) {
          if (pred.test(a, b)) {
            rel.add(new int[] { a, b });
          }
        }
      }

      return rel.toArray(new int[][] {});
    }

    /// Especialització de generateRel per a = b
    static int[][] generateRel(int[] as, BiPredicate<Integer, Integer> pred) {
      return generateRel(as, as, pred);
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 3 (Grafs).
   *
   * Els (di)grafs vendran donats com llistes d'adjacència (és a dir, tractau-los com diccionaris
   * d'adjacència on l'índex és la clau i els vèrtexos estan numerats de 0 a n-1). Per exemple,
   * podem donar el graf cicle d'ordre 3 com:
   *
   * int[][] c3dict = {
   *   {1, 2}, // veïns de 0
   *   {0, 2}, // veïns de 1
   *   {0, 1}  // veïns de 2
   * };
   *
   * **NOTA: Els exercicis d'aquest tema conten doble**
   */
  static class Tema3 {
    /*
     * Determinau si el graf és connex. Podeu suposar que `g` no és dirigit.
     */
    static boolean exercici1(int[][] g) {
      
      int n = g.length;
    boolean[] visited = new boolean[n];

    // Comencem per un node aleatori
    int start = 0;
    visited[start] = true;

    // Realitzem una BFS per visitar tots els nodes
    Queue<Integer> queue = new LinkedList<>();
    queue.add(start);

    while (!queue.isEmpty()) {
        int node = queue.poll();
        for (int[] edge : g) {
            if (edge[0] == node && !visited[edge[1]]) {
                visited[edge[1]] = true;
                queue.add(edge[1]);
            } else if (edge[1] == node && !visited[edge[0]]) {
                visited[edge[0]] = true;
                queue.add(edge[0]);
            }
        }
    }

    // Si tots els nodes han estat visitats, el graf és connex
    for (boolean v : visited) {
        if (v) {
            return false; // Si un node ha estat visitat, el graf no és connex
        }
    }
  
    
      
      return false; // TO DO
    }

    /*
     * Donat un tauler d'escacs d'amplada `w` i alçada `h`, determinau quin és el mínim nombre de
     * moviments necessaris per moure un cavall de la casella `i` a la casella `j`.
     *
     * Les caselles estan numerades de `0` a `w*h-1` per files. Per exemple, si w=5 i h=2, el tauler
     * és:
     *   0 1 2 3 4
     *   5 6 7 8 9
     *
     * Retornau el nombre mínim de moviments, o -1 si no és possible arribar-hi.
     */
    static int exercici2(int w, int h, int i, int j) {
      int n = w * h;
    if (i < 0 || i >= n || j < 0 || j >= n) {
        return -1; // Caselles fora del tauler
    }

    int[] dx = {-2, -1, 1, 2, 2, 1, -1, -2};
    int[] dy = {1, 2, 2, 1, -1, -2, -2, -1};

    Queue<Integer> queue = new LinkedList<>();
    boolean[] visited = new boolean[n];
    int[] distance = new int[n];

    queue.offer(i);
    visited[i] = true;
    distance[i] = 0;

    while (!queue.isEmpty()) {
        int curr = queue.poll();
        if (curr == j) {
            return distance[j];
        }

        for (int k = 0; k < 8; k++) {
            int newX = curr % w + dx[k];
            int newY = curr / w + dy[k];
            int newPos = newX + newY * w;

            if (newX >= 0 && newX < w && newY >= 0 && newY < h && !visited[newPos]) {
                visited[newPos] = true;
                distance[newPos] = distance[curr] + 1;
                queue.offer(newPos);
            }
        }
    }

    return -1; // No és possible arribar a la casella j

    }

    /*
     * Donat un arbre arrelat (graf dirigit `g`, amb arrel `r`), decidiu si el vèrtex `u` apareix
     * abans (o igual) que el vèrtex `v` al recorregut en preordre de l'arbre.
     */
    static boolean exercici3(int[][] g, int r, int u, int v) {
      int n = g.length;
     boolean[] visited = new boolean[n];

    // Realitzem una visita en preordre
    Stack<Integer> stack = new Stack<>();
    stack.push(r);
    visited[r] = true;

    while (!stack.isEmpty()) {
        int node = stack.pop();
        if (node == u) {
            return true; // u apareix abans que v
        }
        if (node == v) {
            return false; // u no apareix abans que v
        }

        for (int[] edge : g) {
            if (edge[0] == node && !visited[edge[1]]) {
                visited[edge[1]] = true;
                stack.push(edge[1]);
            }
        }
    }
      
      
      return false; // TO DO
    }

    /*
     * Donat un recorregut en preordre (per exemple, el primer vèrtex que hi apareix és `preord[0]`)
     * i el grau de cada vèrtex (per exemple, el vèrtex `i` té grau `d[i]`), trobau l'altura de
     * l'arbre corresponent.
     *
     * L'altura d'un arbre arrelat és la major distància de l'arrel a les fulles.
     */
    static int exercici4(int[] preord, int[] d) {

      nt n = preord.length;
    int[] height = new int[n];

    // Inicialitzem la altura de l'arrel a 0
    height[0] = 0;

    // Realitzem una visita en profunditat per trobar la fulla més llunyana
    Stack<Integer> stack = new Stack<>();
    stack.push(0);

    int maxDepth = 0;

    while (!stack.isEmpty()) {
        int node = stack.pop();
        for (int i = 0; i < n; i++) {
            if (preord[i] == node) {
                for (int j = 0; j < n; j++) {
                    if (d[j] == 1 && preord[j] == node) {
                        height[j] = height[node] + 1;
                        stack.push(j);
                        if (height[j] > maxDepth) {
                            maxDepth = height[j];
                        }
                    }
                }
            }
        }
    }
 
      return -1; // TO DO
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // G connex?

      final int[][] B2 = { {}, {} };

      final int[][] C3 = { {1, 2}, {0, 2}, {0, 1} };

      final int[][] C3D = { {1}, {2}, {0} };

      assertThat(exercici1(C3));
      assertThat(!exercici1(B2));

      // Exercici 2
      // Moviments de cavall

      // Tauler 4x3. Moviments de 0 a 11: 3.
      // 0  1   2   3
      // 4  5   6   7
      // 8  9  10  11
      assertThat(exercici2(4, 3, 0, 11) == 3);

      // Tauler 3x2. Moviments de 0 a 2: (impossible).
      // 0 1 2
      // 3 4 5
      assertThat(exercici2(3, 2, 0, 2) == -1);

      // Exercici 3
      // u apareix abans que v al recorregut en preordre (o u=v)

      final int[][] T1 = {
        {1, 2, 3, 4},
        {5},
        {6, 7, 8},
        {},
        {9},
        {},
        {},
        {},
        {},
        {10, 11},
        {},
        {}
      };

      assertThat(exercici3(T1, 0, 5, 3));
      assertThat(!exercici3(T1, 0, 6, 2));

      // Exercici 4
      // Altura de l'arbre donat el recorregut en preordre

      final int[] P1 = { 0, 1, 5, 2, 6, 7, 8, 3, 4, 9, 10, 11 };
      final int[] D1 = { 4, 1, 3, 0, 1, 0, 0, 0, 0, 2,  0,  0 };

      final int[] P2 = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
      final int[] D2 = { 2, 0, 2, 0, 2, 0, 2, 0, 0 };

      assertThat(exercici4(P1, D1) == 3);
      assertThat(exercici4(P2, D2) == 4);
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 4 (Aritmètica).
   *
   * En aquest tema no podeu:
   *  - Utilitzar la força bruta per resoldre equacions: és a dir, provar tots els nombres de 0 a n
   *    fins trobar el que funcioni.
   *  - Utilitzar long, float ni double.
   *
   * Si implementau algun dels exercicis així, tendreu un 0 d'aquell exercici.
   */
  static class Tema4 {
    /*
     * Calculau el mínim comú múltiple de `a` i `b`.
     */
    static int exercici1(int a, int b) {
      
        if (a == 0 || b == 0) {
            return -1; // Retorna -1 si algun dels nombres és zero
        }
        return (Math.abs(a * b)) / gcd(a, b);
    }

    // Mètode per calcular el màxim comú divisor (mcd) utilitzant l'algoritme d'Euclides
    static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    

    /*
     * Trobau totes les solucions de l'equació
     *
     *   a·x ≡ b (mod n)
     *
     * donant els seus representants entre 0 i n-1.
     *
     * Podeu suposar que `n > 1`. Recordau que no no podeu utilitzar la força bruta.
     */
    static int[] exercici2(int a, int b, int n) {
      int d = gcd(a, n);
      
      // No hi ha solució si d no divideix b
      if (b % d != 0) {
          return new int[] {}; // No solution
      }
      
      // Simplificar l'equació dividint per d
      a /= d;
      b /= d;
      n /= d;
      
      // Trobar una solució particular x0
      int x0 = (modInverse(a, n) * b) % n;
      if (x0 < 0) {
          x0 += n;
      }
      
      // Generar totes les solucions
      int[] solucions = new int[d];
      for (int i = 0; i < d; i++) {
          solucions[i] = (x0 + i * n) % (n * d);
      }
      
      return solucions;
  }

  // Mètode per calcular el màxim comú divisor (mcd) utilitzant l'algoritme d'Euclides
  static int gcd(int a, int b) {
      while (b != 0) {
          int temp = b;
          b = a % b;
          a = temp;
      }
      return a;
  }

  // Mètode per trobar l'invers modular utilitzant l'algoritme estès d'Euclides
  static int modInverse(int a, int m) {
      int[] result = extendedGcd(a, m);
      int gcd = result[0];
      int x = result[1];
      int y = result[2];
      if (gcd != 1) {
          throw new ArithmeticException("Inverse does not exist");
      } else {
          return (x % m + m) % m;
      }
  }

  // Mètode per a l'algoritme estès d'Euclides
  static int[] extendedGcd(int a, int b) {
      if (b == 0) {
          return new int[] {a, 1, 0};
      }
      int[] result = extendedGcd(b, a % b);
      int d = result[0];
      int x1 = result[1];
      int y1 = result[2];
      int x = y1;
      int y = x1 - (a / b) * y1;
      return new int[] {d, x, y};
  }
    }

    /*
     * Donats `a != 0`, `b != 0`, `c`, `d`, `m > 1`, `n > 1`, determinau si el sistema
     *
     *   a·x ≡ c (mod m)
     *   b·x ≡ d (mod n)
     *
     * té solució.
     */
    static boolean exercici3(int a, int b, int c, int d, int m, int n) {
      // Primer cal verificar si hi ha solucions per les dues equacions individuals
      if (!hasSolution(a, c, m) || !hasSolution(b, d, n)) {
          return false;
      }

      // Trobar una solució per a·x ≡ c (mod m)
      int x1 = solveSingleCongruence(a, c, m);
      // Trobar una solució per b·x ≡ d (mod n)
      int x2 = solveSingleCongruence(b, d, n);

      // Verificar si x1 i x2 són compatibles per alguna solució comuna
      for (int k = 0; k < m; k++) {
          if ((x1 + k * m) % n == x2) {
              return true;
          }
      }

      return false;
  }

  // Verificar si hi ha solució per a·x ≡ b (mod m)
  static boolean hasSolution(int a, int b, int m) {
      return b % gcd(a, m) == 0;
  }

  // Trobar una solució per a·x ≡ b (mod m) utilitzant l'algoritme estès d'Euclides
  static int solveSingleCongruence(int a, int b, int m) {
      int[] result = extendedGcd(a, m);
      int gcd = result[0];
      int x = result[1];
      int y = result[2];
      if (b % gcd != 0) {
          throw new ArithmeticException("No solution exists");
      }
      int x0 = (x * (b / gcd)) % m;
      if (x0 < 0) {
          x0 += m;
      }
      return x0;
  }

  // Mètode per calcular el màxim comú divisor (mcd) utilitzant l'algoritme d'Euclides
  static int gcd(int a, int b) {
      while (b != 0) {
          int temp = b;
          b = a % b;
          a = temp;
      }
      return a;
  }

  // Mètode per a l'algoritme estès d'Euclides
  static int[] extendedGcd(int a, int b) {
      if (b == 0) {
          return new int[] {a, 1, 0};
      }
      int[] result = extendedGcd(b, a % b);
      int d = result[0];
      int x1 = result[1];
      int y1 = result[2];
      int x = y1;
      int y = x1 - (a / b) * y1;
      return new int[] {d, x, y};
  }

    /*
     * Donats `n` un enter, `k > 0` enter, i `p` un nombre primer, retornau el residu de dividir n^k
     * entre p.
     *
     * Alerta perquè és possible que n^k sigui massa gran com per calcular-lo directament.
     * De fet, assegurau-vos de no utilitzar cap valor superior a max{n, p²}.
     *
     * Anau alerta també abusant de la força bruta, la vostra implementació hauria d'executar-se en
     * qüestió de segons independentment de l'entrada.
     */
    static int exercici4(int n, int k, int p) {
      if (p <= 1 || k <= 0 || n < 0) {
          return -1; // Condicions d'error
      }
      try {
          return modExp(n, k, p);
      } catch (Exception e) {
          return -1; // Captura qualsevol error durant el càlcul
      }
  }

  // Mètode per calcular (base^exponent) % modulus utilitzant l'exponenciació ràpida
  static int modExp(int base, int exponent, int modulus) {
      if (modulus == 1) return 0;
      int result = 1;
      base = base % modulus;
      while (exponent > 0) {
          if ((exponent % 2) == 1) { // Si l'exponent és imparell, multiplica la base pel resultat
              result = (result * base) % modulus;
          }
          exponent = exponent >> 1; // Divideix l'exponent per 2
          base = (base * base) % modulus; // Eleva la base al quadrat
      }
      return result;
  }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // mcm(a, b)

      assertThat(exercici1(35, 77) == 5*7*11);
      assertThat(exercici1(-8, 12) == 24);

      // Exercici 2
      // Solucions de a·x ≡ b (mod n)

      assertThat(Arrays.equals(exercici2(2, 2, 4), new int[] { 1, 3 }));
      assertThat(Arrays.equals(exercici2(3, 2, 4), new int[] { 2 }));

      // Exercici 3
      // El sistema a·x ≡ c (mod m), b·x ≡ d (mod n) té solució?

      assertThat(exercici3(3, 2, 2, 2, 5, 4));
      assertThat(!exercici3(3, 2, 2, 2, 10, 4));

      // Exercici 4
      // n^k mod p

      assertThat(exercici4(2018, 2018, 5) == 4);
      assertThat(exercici4(-2147483646, 2147483645, 679389209) == 145738906);
    }
  }

  /**
   * Aquest mètode `main` conté alguns exemples de paràmetres i dels resultats que haurien de donar
   * els exercicis. Podeu utilitzar-los de guia i també en podeu afegir d'altres (no els tendrem en
   * compte, però és molt recomanable).
   *
   * Podeu aprofitar el mètode `assertThat` per comprovar fàcilment que un valor sigui `true`.
   */
  public static void main(String[] args) {
    Tema1.tests();
    Tema2.tests();
    Tema3.tests();
    Tema4.tests();
  }

  /// Si b és cert, no fa res. Si b és fals, llança una excepció (AssertionError).
  static void assertThat(boolean b) {
    if (!b)
      throw new AssertionError();
  }
}

// vim: set textwidth=100 shiftwidth=2 expandtab :
