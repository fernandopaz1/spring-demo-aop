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
