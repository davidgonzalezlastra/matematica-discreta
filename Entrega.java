import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
    static int exercici1(int n) {
        int count = 0;
        int totalCombinations = (int) Math.pow(2, n); // 2^n combinaciones
        
        for (int i = 0; i < totalCombinations; i++) {
            boolean[] values = new boolean[n];
            for (int j = 0; j < n; j++) {
                values[j] = (i & (1 << j)) != 0; // Genera valores booleanos para p1, p2, ..., pn
            }
            if (evaluateProposition(values)) {
                count++;
            }
        }
        return count;
    }
    
    static boolean evaluateProposition(boolean[] values) {
        int n = values.length;
        boolean result = values[0]; // Comenzar con p1
        for (int i = 1; i < n; i++) {
            result = !result || values[i]; // Evaluar (p1 -> p2) -> p3 -> ... -> pn
        }
        return result;
    }

    /*
     * És cert que ∀x : P(x) -> ∃!y : Q(x,y) ?
     */
    static boolean exercici2(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
        for (int x : universe) {
            if (p.test(x)) {
                // Verificar si existe exactamente un y que satisface Q(x, y)
                boolean existsOneAndOnlyOneY = false;
                for (int y : universe) {
                    if (q.test(x, y)) {
                        if (existsOneAndOnlyOneY) {
                            // Ya se ha encontrado otro y que satisface Q(x, y), por lo que no es único
                            return false;
                        }
                        existsOneAndOnlyOneY = true;
                    }
                }
                // Si no se encontró ningún y o se encontró exactamente uno, continuar con el siguiente x
                if (!existsOneAndOnlyOneY) {
                    return false; // No se encontró ningún y para satisfacer Q(x, y)
                }
            }
        }
        return true; // La afirmación es cierta para todo x en el universo
    }
    /*
     * És cert que ∃x : ∀y : Q(x, y) -> P(x) ?
     */
    static boolean exercici3(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
        for (int x : universe) {
            boolean forAllY = true;
            for (int y : universe) {
                if (!q.test(x, y) || !p.test(x)) {
                    forAllY = false;
                    break;
                }
            }
            if (forAllY) {
                return true; // Existe al menos un x tal que para todos los y, Q(x, y) -> P(x) es verdadero
            }
        }
        return false; // No existe ningún x que satisfaga la afirmación
    }

    /*
     * És cert que ∃x : ∃!y : ∀z : P(x,z) <-> Q(y,z) ?
     */
    static boolean exercici4(int[] universe, BiPredicate<Integer, Integer> p, BiPredicate<Integer, Integer> q) {
        for (int x : universe) {
            int uniqueYCount = 0;
            int uniqueYValue = -1;
            for (int z : universe) {
                boolean pValue = p.test(x, z);
                boolean qValue = false;
                int countY = 0;
                int tempYValue = -1;
                for (int y : universe) {
                    if (q.test(y, z)) {
                        qValue = true;
                        countY++;
                        tempYValue = y;
                    }
                }
                if (pValue != qValue) {
                    return false; // La proposición P(x,z) <-> Q(y,z) no es verdadera para algún z
                }
                if (countY == 1) {
                    uniqueYCount++;
                    uniqueYValue = tempYValue;
                }
            }
            if (uniqueYCount != 1) {
                return false; // No existe exactamente un y que satisfaga Q(y, z)
            }
        }
        return true; // La afirmación es verdadera
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
     * Calculau el nombre d'elements del conjunt (a u b) × (a \ c)
     *
     * Podeu soposar que `a`, `b` i `c` estan ordenats de menor a major.
     */
    static int exercici1(int[] a, int[] b, int[] c) {
        // Tamaño del conjunto a ∪ b
        int sizeUnion = calculateSizeUnion(a, b);
    
        // Tamaño del conjunto a \ c
        int sizeDifference = calculateSizeDifference(a, c);
    
        // Producto cartesiano (a ∪ b) × (a \ c)
        int cartesianProductSize = sizeUnion * sizeDifference;
    
        return cartesianProductSize;
    }
    
    // Método para calcular el tamaño del conjunto a ∪ b
    static int calculateSizeUnion(int[] a, int[] b) {
        int sizeUnion = a.length + b.length;
        int i = 0, j = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j]) {
                i++;
            } else if (a[i] > b[j]) {
                j++;
            } else {
                i++;
                j++;
                sizeUnion--; // Eliminar duplicados
            }
        }
        return sizeUnion;
    }
    
    // Método para calcular el tamaño del conjunto a \ c
    static int calculateSizeDifference(int[] a, int[] c) {
        int sizeDifference = a.length;
        int i = 0, j = 0;
        while (i < a.length && j < c.length) {
            if (a[i] < c[j]) {
                i++;
            } else if (a[i] > c[j]) {
                j++;
            } else {
                i++;
                sizeDifference--; // Eliminar elementos en común con c
            }
        }
        return sizeDifference;
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
        int[][] reflexiveClosure = reflexiveClosure(rel, a.length);
        int[][] symmetricClosure = symmetricClosure(reflexiveClosure);
        int[][] transitiveClosure = transitiveClosure(symmetricClosure);
    
        int cardinality = countElements(transitiveClosure);
        return cardinality;
    }
    
    // Método para calcular la clausura reflexiva
    static int[][] reflexiveClosure(int[][] rel, int n) {
        int[][] reflexiveClosure = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(rel[i], 0, reflexiveClosure[i], 0, n);
            reflexiveClosure[i][i] = 1; // Hacer reflexiva la relación
        }
        return reflexiveClosure;
    }
    
    // Método para calcular la clausura simétrica
    static int[][] symmetricClosure(int[][] rel) {
        int n = rel.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (rel[i][j] == 1) {
                    rel[j][i] = 1; // Hacer simétrica la relación
                }
            }
        }
        return rel;
    }
    
    // Método para calcular la clausura transitiva
    static int[][] transitiveClosure(int[][] rel) {
        int n = rel.length;
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    rel[i][j] |= (rel[i][k] & rel[k][j]); // Hacer transitiva la relación
                }
            }
        }
        return rel;
    }
    
    // Método para contar el número de elementos en la relación
    static int countElements(int[][] rel) {
        int count = 0;
        for (int[] row : rel) {
            for (int elem : row) {
                if (elem == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    /*
     * Comprovau si la relació `rel` és un ordre total sobre `a`. Si ho és retornau el nombre
     * d'arestes del seu diagrama de Hasse. Sino, retornau -2.
     *
     * Podeu soposar que `a` i `rel` estan ordenats de menor a major (`rel` lexicogràficament).
     */
    static int exercici3(int[] a, int[][] rel) {
        if (!isReflexive(a, rel) || !isAntisymmetric(rel) || !isTransitive(rel) || !isTotal(a, rel)) {
            return -2; // La relación no es un orden total
        }
        return countHasseEdges(rel); // Calcular el número de aristas en el diagrama de Hasse
    }
    
    // Método para verificar si la relación es reflexiva
    static boolean isReflexive(int[] a, int[][] rel) {
        for (int i = 0; i < a.length; i++) {
            if (rel[i][i] != 1) {
                return false;
            }
        }
        return true;
    }
    
    // Método para verificar si la relación es antisimétrica
    static boolean isAntisymmetric(int[][] rel) {
        int n = rel.length;
        for (int i = 0; i < n; i++) {
            if (rel[i][i] == 1) {
                return false; // La relación debe ser estricta
            }
            for (int j = i + 1; j < n; j++) {
                if (rel[i][j] == 1 && rel[j][i] == 1) {
                    return false; // La relación no es antisimétrica
                }
            }
        }
        return true;
    }
    
    // Método para verificar si la relación es transitiva
    static boolean isTransitive(int[][] rel) {
        int n = rel.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (rel[i][j] == 1 && rel[j][k] == 1 && rel[i][k] != 1) {
                        return false; // La relación no es transitiva
                    }
                }
            }
        }
        return true;
    }
    
    // Método para verificar si la relación es total
    static boolean isTotal(int[] a, int[][] rel) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            boolean hasSmaller = false;
            boolean hasGreater = false;
            for (int j = 0; j < n; j++) {
                if (rel[i][j] == 1) {
                    hasGreater = true;
                }
                if (rel[j][i] == 1) {
                    hasSmaller = true;
                }
            }
            if (!hasSmaller || !hasGreater) {
                return false; // No es total
            }
        }
        return true;
    }
    
    // Método para contar el número de aristas en el diagrama de Hasse
    static int countHasseEdges(int[][] rel) {
        int count = 0;
        int n = rel.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (rel[i][j] == 1 && rel[j][i] == 1 && i < j) {
                    count++; // Hay una arista entre i y j en el diagrama de Hasse
                }
            }
        }
        return count;
    }


    /*
     * Comprovau si les relacions `rel1` i `rel2` són els grafs de funcions amb domini i codomini
     * `a`. Si ho son, retornau el graf de la composició `rel2 ∘ rel1`. Sino, retornau null.
     *
     * Podeu soposar que `a`, `rel1` i `rel2` estan ordenats de menor a major (les relacions,
     * lexicogràficament).
     */
    static int[][] exercici4(int[] a, int[][] rel1, int[][] rel2) {
        if (!isValidFunction(a, rel1) || !isValidFunction(a, rel2)) {
            return null; // Si alguna de las relaciones no es una función válida, retornar null
        }
    
        // Calcular la composición rel2 ∘ rel1
        return composition(rel1, rel2);
    }
    
    // Método para verificar si la relación es una función válida con dominio y codominio a
    static boolean isValidFunction(int[] a, int[][] rel) {
        for (int[] pair : rel) {
            if (pair.length != 2 || !contains(a, pair[0]) || !contains(a, pair[1])) {
                return false; // Si el par no tiene exactamente dos elementos o algún elemento no está en a, retornar false
            }
        }
        return true;
    }
    
    // Método auxiliar para verificar si un elemento está en el arreglo
    static boolean contains(int[] arr, int target) {
        for (int num : arr) {
            if (num == target) {
                return true;
            }
        }
        return false;
    }
    
    // Método para calcular la composición rel2 ∘ rel1
    static int[][] composition(int[][] rel1, int[][] rel2) {
        List<int[]> result = new ArrayList<>();
        for (int[] pair1 : rel1) {
            for (int[] pair2 : rel2) {
                if (pair1[1] == pair2[0]) { // Si el segundo elemento de rel1 coincide con el primer elemento de rel2
                    result.add(new int[]{pair1[0], pair2[1]}); // Agregar el par (primer elemento de rel1, segundo elemento de rel2) a la composición
                }
            }
        }
        return result.toArray(new int[0][]);
    }
    /*
     * Comprovau si la funció `f` amb domini `dom` i codomini `codom` té inversa. Si la té, retornau
     * el seu graf (el de l'inversa). Sino, retornau null.
     */
    static int[][] exercici5(int[] dom, int[] codom, Function<Integer, Integer> f) {
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
        if (g == null || g.length == 0) {
            return false; // Grafo vacío o nulo
        }
    
        int n = g.length;
        boolean[] visited = new boolean[n];
    
        // Empezar la búsqueda desde el vértice 0
        dfs(g, 0, visited);
    
        // Verificar si todos los vértices fueron visitados
        for (boolean vertexVisited : visited) {
            if (!vertexVisited) {
                return false; // El grafo no es conexo
            }
        }
    
        return true; // El grafo es conexo
    }
    
    // Método auxiliar para búsqueda en profundidad (DFS)
    static void dfs(int[][] g, int vertex, boolean[] visited) {
        visited[vertex] = true;
        for (int neighbor : g[vertex]) {
            if (!visited[neighbor]) {
                dfs(g, neighbor, visited);
            }
        }
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
        int startX = i % w;
        int startY = i / w;
        int targetX = j % w;
        int targetY = j / w;

        if (targetX < 0 || targetX >= w || targetY < 0 || targetY >= h) {
            return -1;
        }

        boolean[][] visited = new boolean[h][w];

        int[][] moves = {{-2, -1}, {-1, -2}, {1, -2}, {2, -1}, {2, 1}, {1, 2}, {-1, 2}, {-2, 1}};

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startX, startY, 0});
        visited[startY][startX] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int movesCount = current[2];

            if (x == targetX && y == targetY) {
                return movesCount;
            }

            for (int[] move : moves) {
                int newX = x + move[0];
                int newY = y + move[1];

                if (newX >= 0 && newX < w && newY >= 0 && newY < h && !visited[newY][newX]) {
                    queue.offer(new int[]{newX, newY, movesCount + 1});
                    visited[newY][newX] = true;
                }
            }
        }

        return -1;
    }


    /*
     * Donat un arbre arrelat (graf dirigit `g`, amb arrel `r`), decidiu si el vèrtex `u` apareix
     * abans (o igual) que el vèrtex `v` al recorregut en preordre de l'arbre.
     */
    static boolean exercici3(int[][] g, int r, int u, int v) {
        // Realizar el recorrido en preorden del árbol
        List<Integer> preorder = new ArrayList<>();
        dfs(g, r, preorder);

        // Verificar el orden de aparición de u y v en el recorrido en preorden
        int indexU = preorder.indexOf(u);
        int indexV = preorder.indexOf(v);
        return indexU != -1 && indexV != -1 && indexU <= indexV;
    }

    // Método auxiliar para recorrido en preorden (DFS)
    static void dfs(int[][] g, int node, List<Integer> preorder) {
        preorder.add(node);
        for (int neighbor : g[node]) {
            dfs(g, neighbor, preorder);
        }
    }
    /*
     * Donat un recorregut en preordre (per exemple, el primer vèrtex que hi apareix és `preord[0]`)
     * i el grau de cada vèrtex (per exemple, el vèrtex `i` té grau `d[i]`), trobau l'altura de
     * l'arbre corresponent.
     *
     * L'altura d'un arbre arrelat és la major distància de l'arrel a les fulles.
     */
    static int exercici4(int[] preord, int[] d) {
        // Variable para mantener la altura máxima del árbol
        int maxHeight = 0;

        // Llamada inicial a la función recursiva
        maxHeight = calculateHeight(preord, d, 0, preord.length, 0, maxHeight);

        return maxHeight;
    }

    // Función recursiva para calcular la altura del árbol
    static int calculateHeight(int[] preord, int[] d, int start, int end, int height, int maxHeight) {
        // Si no hay nodos en el rango actual, retornamos la altura actual
        if (start >= end) {
            return height;
        }

        // Encontramos el grado del primer nodo en el rango actual
        int degree = d[preord[start]];

        // Incrementamos la altura para cada nodo en el rango actual
        height++;

        // Actualizamos la altura máxima si es necesario
        maxHeight = Math.max(maxHeight, height);

        // Buscamos el inicio del siguiente rango que es el hijo del nodo actual
        int nextStart = start + 1;
        while (nextStart < end && degree < d[preord[nextStart]]) {
            nextStart++;
        }

        // Llamamos recursivamente a la función para cada subárbol del nodo actual
        for (int i = start + 1; i < nextStart; i++) {
            maxHeight = calculateHeight(preord, d, i, nextStart, height, maxHeight);
        }

        // Llamamos recursivamente a la función para el siguiente rango
        maxHeight = calculateHeight(preord, d, nextStart, end, height, maxHeight);

        return maxHeight;
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
      return -1; // TO DO
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
      return new int[] {}; // TO DO
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
      return false; // TO DO
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
      return -1; // TO DO
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    /* 
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
      assertThat(exercici4(-2147483646, 2147483645, 46337) == 7435);
      */
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