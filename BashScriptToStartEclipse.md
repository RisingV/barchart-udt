# Eclipse #
When you need to use a new eclipse version, not yet available for your linux distro, you may need to make an 'eclipse-start.sh' bash script which is pointed to by your desktop link. Example below guarantees that your eclipse starts with the same environment as your shell, and all changes you make to your '.bashrc' file are available to eclipse after eclipse restart instead of KDE/Gnome restart. To verify what is actually in the eclipse environment, look into 'eclipse.env' file.


# eclipse-start.sh #
```
#!/bin/bash
export BASH_ENV="~/.bashrc"
bash -c "env | sort > ~/.eclipse/eclipse.env; /opt/eclipse-3.6/eclipse" &
```