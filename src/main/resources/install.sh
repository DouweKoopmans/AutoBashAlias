#!/usr/bin/env bash
exec ./bin/AutoBashAlias addalias $(pwd)/bin/AutoBashAlias
source ~/.profile
echo installed AutoBashAlias, you can now use \"addalias\" to execute it
