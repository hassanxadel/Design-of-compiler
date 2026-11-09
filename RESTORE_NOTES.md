# Restore Notes

## Project summary
A small desktop app for a "Design of Compilers" university assignment: a **lexical analyzer (scanner)** for the TINY language, built with Java + JavaFX. You paste TINY source code into a text box, press **Scan**, and it lists every token it recognized (identifiers, keywords, numbers, operators, punctuation) next to its token type.

- **Language/runtime:** Java 17
- **UI toolkit:** JavaFX (controls + FXML)
- **Build tool:** Maven (added during restore — the project previously had no build file at all)
- **Entry point:** `tiny.Tiny` (`src/main/java/tiny/Tiny.java`)

## How to install, build, and run
Prerequisites: JDK 17+ and Maven 3.6+ on your PATH.

```bash
mvn clean compile      # build
mvn javafx:run         # launch the GUI
```

`mvn package` also produces a runnable jar under `target/`.

## What was broken and what was fixed
The project had no build system at all — just six loose `.java`/`.fxml` files sitting in a `Code Files/tiny/` folder, so it could not be compiled or run on this machine out of the box (Java 17 no longer bundles JavaFX).

| File | Problem | Fix |
|---|---|---|
| *(whole project)* | No `pom.xml`, no NetBeans/Maven/Gradle project file; source wasn't in a standard layout | Added `pom.xml` with the `org.openjfx` JavaFX 17 dependencies + `javafx-maven-plugin`; moved sources to the standard `src/main/java` / `src/main/resources` Maven layout |
| `Tiny.java` | JavaFX isn't bundled in modern JDKs, so the app couldn't launch without declaring JavaFX as a dependency | Resolved via the new `pom.xml`; also wired up the new stylesheet and gave the window a sensible minimum size |
| *(scanner logic)* | — | None found. The `Scanner`/`Token`/`States` DFA was tested against the original sample program from the project's own screenshots and reproduced the exact same 34 tokens, so no logic bugs were fixed — the core lexer was already correct. |

A smoke test (`src/test/java/tiny/ScannerSmokeTest.java`) exercises the scanner against that sample program and asserts the token count, as a quick regression check for future changes.

## Dependency/SDK changes and why
- **Added Maven** as the build tool (`pom.xml`) — the project had none, and without one it can't be built on a current machine.
- **Added JavaFX 17 (`org.openjfx:javafx-controls`, `javafx-fxml`)** as explicit dependencies — required because JavaFX was removed from the JDK itself starting with Java 11; it must now be pulled in separately.
- No other dependency versions changed; the original Java language/source stayed untouched.

## UI improvements
The original UI was a plain white text box with a default-styled "Scan" button. It was restyled to a dark, IDE-like look that fits a compiler/scanner tool, using only JavaFX/FXML/CSS (no new UI framework):
- Dark slate background with a teal accent color, monospace code font for the input/output area.
- Header with a title and subtitle so the tool's purpose is clear at a glance.
- Status line that reports the result ("Ready", "N tokens found", or a prompt to enter code if you hit Scan on an empty box) instead of just a blank/green button.
- Window is now resizable (with a sensible minimum size) instead of fixed-size, using a `BorderPane` layout that keeps the header/editor/footer each filling their own space.
- Scan button now has hover/pressed states.

All original behavior is unchanged: same Scan / Re-enter toggle, same token output format, same token grammar.

## Removed
- `Executable/TINY compiler.exe` — a stale, pre-built 2021 jar/launcher bundle. It predates this restore, doesn't reflect the new UI, and would not run reliably on a modern JDK without a bundled JavaFX runtime. Build a fresh one with `mvn package` (or `mvn javafx:run` to just launch it) instead.

## Known limitations
- The scanner is intentionally simple (matches the original assignment scope): it has no error reporting for invalid tokens or unterminated `{ ... }` comments — those are just silently dropped, as in the original implementation. This wasn't changed, to keep behavior identical to the original.
- No automated UI tests (JavaFX UI testing requires extra tooling like TestFX, which wasn't part of the original project). `ScannerSmokeTest.java` covers the underlying scanner logic instead.
