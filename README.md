# purescript-uuid

## JVM tests

`./bin/test` selects `uuid` in the [common isolated runner](../javapurs/docs/testing.md#port-particulier). It awaits the original Spec tests, rejects failures/pending results, and requires all six successes and a completion marker. Five negative probes cover the waiting boundary and captured Spec failures.
Use `./bin/test --help` for options and `./bin/test --clean` to rebuild the backend. The guide covers prerequisites, Java settings and retained failure logs; this checkout and its outputs are preserved.

[![Build & Test](https://github.com/megamaddu/purescript-uuid/actions/workflows/node.js.yml/badge.svg)](https://github.com/megamaddu/purescript-uuid/actions/workflows/node.js.yml)

Wrapper for the `uuid` npm package.
This package is not installed automatically, as it comes from `npm`.
Install it with `npm install -S uuid`.
