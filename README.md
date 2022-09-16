# spring-demo-aop

Los aspects son una forma de implementar una lógica de casos de uso trasnsversal a muchos metodos que las tengan en comun, estos casos pueden ser loggin, validaciones, seguridad, etc.

<sub>

    @Aspect
    @Component
    public class MyDemoLogginAspect {

            @Before("execution(public void addAccount())")
            public void beforeAddAccountAdvice() {
                System.out.println("\n======>>> Executing @Before advice");
            }

    }

</sub>

LLamamos al string "execution(public void addAccount())" pointcut expresion porque indica que el patron que lanza la ejecucion del parttern.

Especificamos los metodos que ejecutan este aspect mediante la annotation @Before pasando como parametro el string "execution(...)", con la signature del metodo. Todos los metodos que compartan esta signature ejecutaran este aspect cada vez que sean llamadas.

## Pintcut Expresion general

`execution(modifiers-pattern? return-type-pattern declaring-type-pattern method-name-pattern(param-pattern) throws-pattern?)`

Este es un poincut expresion general, donde los signos de pregunta indican que el patrón es opcional.

Por ejemplo si quisieramos hacer match de un solo metodo en particular podemos agregar agregar uno de los patrones opcionales que es `declaring-type-pattern`, la clase que declara el metodo

`@Before("execution(public void com.luv2code.aopdemo.dao.AccountDAO.addAccount())")`
en cambio obviando ese patron el pointcut expresion matchea los metodos de cualquier clase

`@Before("execution(public void addAccount())")`

Este matchea todos los metodos publicos que devuelven void y que empiezan con `add`
`@Before("execution(public void add*())")`

También se puede usar wildcards en el tipo de retorno
`@Before("execution(public * processCreditCard*())")`

### Matcheo de parametros de

- () : Matchea los metodos sin argumentos
- (\*) : Matchea los metodos con un argumento de cualquier tipo
- (..) : Matchea los metodos con 0 o mas argumentos de cualquier tipo

Si pasamos como parametro una expresion que matchea una clase con el nombre completamente especificado solo va a matchear ese tipo de parametros.

`com.luv2code.aopdemo.Account`

También podemos matchear cualquier metodo de de un paquete

`"execution(* com.luv2code.aopdemo.dao.*.*(..))"`

el primer `*` se corresponde con el return type, el segundo `*` es la clase y el tercer `*` es el método.

### Reutilizar pointcut expresion

Si queremos ejecutar varios advices bajo un mismo pointcut expresion lo que debemos hacer es declararla aparte

<sub>

    @Aspect
    @Component
    public class MyDemoLogginAspect {

            @Pointcut("execution(* com.luv2code.aopdemo.dao.*.*(..)")
            private void forDaoPackage(){}

            @Before("forDaoPackage()")
            public void beforeAddAccountAdvice() {
                ...
            }

            @Before("forDaoPackage()")
            public void performApiAnalitycs() {
                ...
            }
    }

</sub>

de esta forma el primer pointcut se declara y al ajecutarse lanza los otros dos

### Combinando Pointcut

- `@Before("expresionOne() && expresionTwo()")` : Matchea ambos
- `@Before("expresionOne() || expresionTwo()")` : Machea cualquiera de los dos
- `@Before("expresionOne() && !expresionTwo()")`: Matchea solo expresionOne

<sub>

    @Aspect
    @Component
    public class MyDemoLogginAspect {

            @Pointcut("execution(* com.luv2code.aopdemo.dao.*.*(..)")
            private void forDaoPackage(){}

            // matchea todos los getters
            @Pointcut("execution(* com.luv2code.aopdemo.dao.*.get*(..)")
            private void getter() {
                ...
            }

            // matchea todos los setters
            @Pointcut("execution(* com.luv2code.aopdemo.dao.*.set*(..)")
            private void setter() {
                ...
            }

            // combina los poincut: Todos los metodos del package excepto los getters y setters
            @Pointcut("forDaoPackage() && !(getter() || setter())")
            private void forDaoPackageNoGetterSetter(){}

            @Before("forDaoPackageNoGetterSetter()")
            public void beforeAddAdvice(){
                ...
            }
    }

</sub>

### Orden

Para ordenar la que aspects se ejecutan primero usamos la annotation `@Order` y le pasamos un numero. Entre mas chico sea el numero mas prioridad tiene el aspcet.
Puede tener valores negativos siendo el mas negativo el de mas prioridad.
Si tienen el mismo numero de orden entonces la prioridad es indefinida.
