using System.Collections;
using UnityEngine;

public class collison : MonoBehaviour
{
    public move player;
    public score a;
    public show b;
    private int i = 0;
    Renderer render;
    Animator animator;
    public Material[] material;
    public colorShow show_color;
    private float timer;

    void Start()
    {
        render = GetComponentInChildren<SkinnedMeshRenderer>();
        animator = GetComponentInChildren<Animator>();
        render.enabled = true;
        material[0] = data.body;
    }

    private void Update()
    {
        timer += Time.deltaTime; 
    }

    private void OnTriggerEnter(Collider collider)
        {
        if (collider.tag != "floor" && collider.tag != "Untagged")
        {
            if (i < 3 && i > 0)
            {
                if (collider.tag == transform.tag.ToLower())
                {
                    show_color.setColor(i, check_color(collider.tag));
                    collider.gameObject.name = "delete";
                    i++;
                    changeColor(collider.tag);
                }
                else
                {
                    collider.isTrigger = false;
                    player.enabled = false;
                    a.end();
                    animator.SetInteger("animation", 7);
                }
                timer = 0;
            }

            if (i == 0 && collider.tag != "Untagged")
            {
                show_color.setColor(i, check_color(collider.tag));
                transform.tag = collider.tag.ToUpper();
                collider.gameObject.name = "delete";
                i++;
                changeColor(collider.tag);
                timer = 0;
            }

            if (i == 3)
            {
                collider.gameObject.name = "delete";
                transform.tag = "Player";
                i = 0;
                a.set();
                render.material = material[0];
                b.add(collider.tag);
                timer = 0;
            }
        }
    }

    int check_color(string color)
    {
        int i = 0;
        switch (color)
        {
            case "blue":i = 0;break;
            case "yellow":i = 1;break;
            case "red":i = 2;break;
        }
        return i;
    }

    void changeColor(string a)
    {
        switch (a)
        {
            case "yellow": render.material = material[1];break;
            case "blue": render.material = material[2]; break;
            case "red": render.material = material[3]; break;
        }
    }

    public float getTimer()
    {
        return timer;
    }
}
