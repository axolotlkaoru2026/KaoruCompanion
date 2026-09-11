# やぁ Live Character Test V0.1

Android Live Wallpaper prototype using the supplied やぁ model sheet as the visual source.

## V0.1 features
- Live Wallpaper
- continuous 60-ish FPS render loop (device dependent)
- subtle breathing/bobbing/sway motion (prototype)
- direct wallpaper tap interaction
- tap position changes the mood effect (upper area vs lower area)
- persistent mood/loneliness state
- widget tap interaction
- transparent wallpaper canvas so the normal home screen remains visible underneath
- orientation/resolution independent normalized positioning

## Important prototype limitation
The supplied image is a model sheet, not animation-ready separated character layers. V0.1 therefore uses a cropped front-view source and procedural motion. It does **not** yet provide true facial morphing, eyelid animation, hair physics, or a true 3D turn-away animation. Those belong in V0.2+ after preparing layered/mesh-ready character assets.

## Build
Open this project in Android Studio and build `app` -> `assembleDebug`.

A GitHub Actions workflow is included at `.github/workflows/build.yml` so the project can also be built from a phone using a GitHub repository and the browser.
