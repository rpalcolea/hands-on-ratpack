import ratpack.registry.RegistrySpec

import static ratpack.groovy.Groovy.ratpack

ratpack {
  handlers {
    // For this use case there are better options than this.  We will see in future labs
    register { RegistrySpec spec ->
      spec.add(DefaultPersonService.instance)
    }

    prefix("person/:id") {
      get("name") {
        // Objects added to the registry by upstream handlers are available via type-lookup
        PersonService personService = context.get(PersonService)

        //TODO refactor this into a common handler for this chain
        long id = allPathTokens.asLong("id")
        Person p = personService.getPerson(id)

        response.send p.name
      }

      get("status") { PersonService personService -> // Registry objects can also be "injected" into handler closures
        //TODO refactor this into a common handler for this chain
        long id = allPathTokens.asLong("id")
        Person p = personService.getPerson(id)

        response.send p.status
      }
    }
  }
}
