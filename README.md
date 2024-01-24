# nextjournal.garden-clerk

A helper library for serving [clerk](https://clerk.vision) notebooks on [application.garden](https://application.garden).

## Usage

To create a static build for a clerk notebook and serve it using a simple webserver, use something like this `deps.edn`:

```edn
{:deps {io.github.nextjournal/garden-clerk {:git/sha "<version>"}}
 :aliases
 {:nextjournal/garden {:exec-fn nextjournal.garden-clerk/serve-static!
                       :exec-args {:paths ["hello.md" "world.clj"]
                                   :index "hello.md"}}}}
```

Arguments passed in `exec-args` are passed through to `nextjournal.clerk/build!`.
