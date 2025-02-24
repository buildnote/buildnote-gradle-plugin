# see https://github.com/casey/just

_targets:
  @just --list --unsorted --list-heading $'Available targets:\n' --list-prefix "  "

_gradle_cache_usage gradle_user_home=env_var_or_default("GRADLE_USER_HOME", "~/.gradle"):
  #!/usr/bin/env sh
  echo ">>> Gradle cache usage"
  if sort -h {{justfile()}} &> /dev/null; then
    du -h -d 1 {{gradle_user_home}}/caches/ | sort -hr
  else
    du -d 1 {{gradle_user_home}}/caches/ \
      | sort -k 1 -nr \
      | awk '{ if ($1 > 1024^4) { printf "%.1fP\t%s\n", $1/1024^4+0.05,$2 }
          else if ($1 > 1024^3) { printf "%.1fT\t%s\n", $1/1024^3+0.05,$2 }
          else if ($1 > 1024^2) { printf "%.1fG\t%s\n", $1/1024^2+0.05,$2 }
          else if ($1 > 1024^1) { printf "%.1fM\t%s\n", $1/1024^1+0.05,$2 }
          else                  { printf "%.1fK\t%s\n", $1/1024^0+0.05,$2 }
        }'
  fi

_gradle +args="--version":
  #!/usr/bin/env sh
  cd {{justfile_directory()}} && ./gradlew {{args}}

_gradle_task task: (_gradle task)

# clean everything
clean: (_gradle "clean")

# build project
check: (_gradle_task "check")

# rebuild everything
rerun: (_gradle_task "clean check --rerun-tasks")