/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.widgets.shared.services.code;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

public class IndustryICBCodeType extends AbstractCodeType<Long, Long> {

  private static final long serialVersionUID = 1L;
  public static final Long ID = 0000L;

  public IndustryICBCodeType() {
    super();
  }

  @Override
  protected boolean getConfiguredIsHierarchy() {
    return true;
  }

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("IndustryICB");
  }

  @Override
  public Long getId() {
    return ID;
  }

  // code=0001 text=Oil & Gas values[x]=0001 Oil & Gas
  @Order(10)
  public static class ICB0001 extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final Long ID = 0001L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ICB0001");
    }

    @Override
    public Long getId() {
      return ID;
    }

    // code=0500 text=Oil & Gas values[x]=0500 Oil & Gas
    @Order(10)
    public static class ICB0500 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 0500L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB0500");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=0530 text=Oil & Gas Producers values[x]=0530 Oil & Gas Producers
      @Order(10)
      public static class ICB0530 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 0530L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB0530");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=0533 text=Exploration & Production values[x]=0533 Exploration & Production
        @Order(10)
        public static class ICB0533 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 0533L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB0533");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=0537 text=Integrated Oil & Gas values[x]=0537 Integrated Oil & Gas
        @Order(10)
        public static class ICB0537 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 0537L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB0537");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=0570 text=Oil Equipment, Services & Distribution values[x]=0570 Oil Equipment, Services & Distribution
      @Order(10)
      public static class ICB0570 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 0570L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB0570");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=0573 text=Oil Equipment & Services values[x]=0573 Oil Equipment & Services
        @Order(10)
        public static class ICB0573 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 0573L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB0573");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=0577 text=Pipelines values[x]=0577 Pipelines
        @Order(10)
        public static class ICB0577 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 0577L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB0577");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }
  }

  // code=1000 text=Basic Materials values[x]=1000 Basic Materials
  @Order(10)
  public static class ICB1000 extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final Long ID = 1000L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ICB1000");
    }

    @Override
    public Long getId() {
      return ID;
    }

    // code=1300 text=Chemicals values[x]=1300 Chemicals
    @Order(10)
    public static class ICB1300 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 1300L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB1300");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=1350 text=Chemicals values[x]=1350 Chemicals
      @Order(10)
      public static class ICB1350 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 1350L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB1350");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=1353 text=Commodity Chemicals values[x]=1353 Commodity Chemicals
        @Order(10)
        public static class ICB1353 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1353L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1353");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=1357 text=Specialty Chemicals values[x]=1357 Specialty Chemicals
        @Order(10)
        public static class ICB1357 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1357L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1357");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }

    // code=1700 text=Basic Resources values[x]=1700 Basic Resources
    @Order(10)
    public static class ICB1700 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 1700L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB1700");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=1730 text=Forestry & Paper values[x]=1730 Forestry & Paper
      @Order(10)
      public static class ICB1730 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 1730L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB1730");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=1733 text=Forestry values[x]=1733 Forestry
        @Order(10)
        public static class ICB1733 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1733L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1733");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=1737 text=Paper values[x]=1737 Paper
        @Order(10)
        public static class ICB1737 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1737L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1737");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=1750 text=Industrial Metals values[x]=1750 Industrial Metals
      @Order(10)
      public static class ICB1750 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 1750L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB1750");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=1753 text=Aluminum values[x]=1753 Aluminum
        @Order(10)
        public static class ICB1753 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1753L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1753");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=1755 text=Nonferrous Metals values[x]=1755 Nonferrous Metals
        @Order(10)
        public static class ICB1755 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1755L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1755");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=1757 text=Steel values[x]=1757 Steel
        @Order(10)
        public static class ICB1757 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1757L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1757");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=1770 text=Mining values[x]=1770 Mining
      @Order(10)
      public static class ICB1770 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 1770L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB1770");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=1771 text=Coal values[x]=1771 Coal
        @Order(10)
        public static class ICB1771 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1771L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1771");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=1773 text=Diamonds & Gemstones values[x]=1773 Diamonds & Gemstones
        @Order(10)
        public static class ICB1773 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1773L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1773");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=1775 text=General Mining values[x]=1775 General Mining
        @Order(10)
        public static class ICB1775 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1775L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1775");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=1777 text=Gold Mining values[x]=1777 Gold Mining
        @Order(10)
        public static class ICB1777 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1777L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1777");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=1779 text=Platinum & Precious Metals values[x]=1779 Platinum & Precious Metals
        @Order(10)
        public static class ICB1779 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1779L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1779");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }
  }

  // code=2000 text=Industrials values[x]=2000 Industrials
  @Order(10)
  public static class ICB2000 extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final Long ID = 2000L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ICB2000");
    }

    @Override
    public Long getId() {
      return ID;
    }

    // code=2300 text=Construction & Materials values[x]=2300 Construction & Materials
    @Order(10)
    public static class ICB2300 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 2300L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB2300");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=2350 text=Construction & Materials values[x]=2350 Construction & Materials
      @Order(10)
      public static class ICB2350 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 2350L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB2350");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=2353 text=Building Materials & Fixtures values[x]=2353 Building Materials & Fixtures
        @Order(10)
        public static class ICB2353 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2353L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2353");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2357 text=Heavy Construction values[x]=2357 Heavy Construction
        @Order(10)
        public static class ICB2357 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2357L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2357");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }

    // code=2700 text=Industrial Goods & Services values[x]=2700 Industrial Goods & Services
    @Order(10)
    public static class ICB2700 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 2700L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB2700");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=2710 text=Aerospace & Defense values[x]=2710 Aerospace & Defense
      @Order(10)
      public static class ICB2710 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 2710L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB2710");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=2713 text=Aerospace values[x]=2713 Aerospace
        @Order(10)
        public static class ICB2713 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2713L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2713");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2717 text=Defense values[x]=2717 Defense
        @Order(10)
        public static class ICB2717 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2717L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2717");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=2720 text=General Industrials values[x]=2720 General Industrials
      @Order(10)
      public static class ICB2720 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 2720L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB2720");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=2723 text=Containers & Packaging values[x]=2723 Containers & Packaging
        @Order(10)
        public static class ICB2723 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2723L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2723");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2727 text=Diversified Industrials values[x]=2727 Diversified Industrials
        @Order(10)
        public static class ICB2727 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2727L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2727");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=2730 text=Electronic & Electrical Equipment values[x]=2730 Electronic & Electrical Equipment
      @Order(10)
      public static class ICB2730 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 2730L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB2730");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=2733 text=Electrical Components & Equipment values[x]=2733 Electrical Components & Equipment
        @Order(10)
        public static class ICB2733 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2733L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2733");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2737 text=Electronic Equipment values[x]=2737 Electronic Equipment
        @Order(10)
        public static class ICB2737 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2737L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2737");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=2750 text=Industrial Engineering values[x]=2750 Industrial Engineering
      @Order(10)
      public static class ICB2750 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 2750L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB2750");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=2753 text=Commercial Vehicles & Trucks values[x]=2753 Commercial Vehicles & Trucks
        @Order(10)
        public static class ICB2753 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2753L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2753");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2757 text=Industrial Machinery values[x]=2757 Industrial Machinery
        @Order(10)
        public static class ICB2757 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2757L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2757");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=2770 text=Industrial Transportation values[x]=2770 Industrial Transportation
      @Order(10)
      public static class ICB2770 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 2770L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB2770");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=2771 text=Delivery Services values[x]=2771 Delivery Services
        @Order(10)
        public static class ICB2771 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2771L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2771");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2773 text=Marine Transportation values[x]=2773 Marine Transportation
        @Order(10)
        public static class ICB2773 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2773L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2773");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2775 text=Railroads values[x]=2775 Railroads
        @Order(10)
        public static class ICB2775 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2775L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2775");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2777 text=Transportation Services values[x]=2777 Transportation Services
        @Order(10)
        public static class ICB2777 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2777L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2777");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2779 text=Trucking values[x]=2779 Trucking
        @Order(10)
        public static class ICB2779 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2779L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2779");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=2790 text=Support Services values[x]=2790 Support Services
      @Order(10)
      public static class ICB2790 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 2790L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB2790");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=2791 text=Business Support Services values[x]=2791 Business Support Services
        @Order(10)
        public static class ICB2791 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2791L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2791");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2793 text=Business Training & Employment Agencies values[x]=2793 Business Training & Employment Agencies
        @Order(10)
        public static class ICB2793 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2793L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2793");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2795 text=Financial Administration values[x]=2795 Financial Administration
        @Order(10)
        public static class ICB2795 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2795L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2795");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2797 text=Industrial Suppliers values[x]=2797 Industrial Suppliers
        @Order(10)
        public static class ICB2797 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2797L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2797");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2799 text=Waste & Disposal Services values[x]=2799 Waste & Disposal Services
        @Order(10)
        public static class ICB2799 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2799L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2799");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }
  }

  // code=3000 text=Consumer Goods values[x]=3000 Consumer Goods
  @Order(10)
  public static class ICB3000 extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final Long ID = 3000L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ICB3000");
    }

    @Override
    public Long getId() {
      return ID;
    }

    // code=3300 text=Automobiles & Parts values[x]=3300 Automobiles & Parts
    @Order(10)
    public static class ICB3300 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 3300L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB3300");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=3350 text=Automobiles & Parts values[x]=3350 Automobiles & Parts
      @Order(10)
      public static class ICB3350 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 3350L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB3350");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=3353 text=Automobiles values[x]=3353 Automobiles
        @Order(10)
        public static class ICB3353 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3353L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3353");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3355 text=Auto Parts values[x]=3355 Auto Parts
        @Order(10)
        public static class ICB3355 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3355L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3355");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3357 text=Tires values[x]=3357 Tires
        @Order(10)
        public static class ICB3357 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3357L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3357");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }

    // code=3500 text=Food & Beverage values[x]=3500 Food & Beverage
    @Order(10)
    public static class ICB3500 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 3500L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB3500");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=3530 text=Beverages values[x]=3530 Beverages
      @Order(10)
      public static class ICB3530 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 3530L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB3530");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=3533 text=Brewers values[x]=3533 Brewers
        @Order(10)
        public static class ICB3533 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3533L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3533");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3535 text=Distillers & Vintners values[x]=3535 Distillers & Vintners
        @Order(10)
        public static class ICB3535 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3535L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3535");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3537 text=Soft Drinks values[x]=3537 Soft Drinks
        @Order(10)
        public static class ICB3537 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3537L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3537");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=3570 text=Food Producers values[x]=3570 Food Producers
      @Order(10)
      public static class ICB3570 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 3570L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB3570");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=3573 text=Farming & Fishing values[x]=3573 Farming & Fishing
        @Order(10)
        public static class ICB3573 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3573L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3573");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3577 text=Food Products values[x]=3577 Food Products
        @Order(10)
        public static class ICB3577 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3577L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3577");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }

    // code=3700 text=Personal & Household Goods values[x]=3700 Personal & Household Goods
    @Order(10)
    public static class ICB3700 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 3700L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB3700");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=3720 text=Household Goods values[x]=3720 Household Goods
      @Order(10)
      public static class ICB3720 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 3720L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB3720");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=3722 text=Durable Household Products values[x]=3722 Durable Household Products
        @Order(10)
        public static class ICB3722 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3722L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3722");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3724 text=Nondurable Household Products values[x]=3724 Nondurable Household Products
        @Order(10)
        public static class ICB3724 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3724L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3724");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3726 text=Furnishings values[x]=3726 Furnishings
        @Order(10)
        public static class ICB3726 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3726L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3726");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3728 text=Home Construction values[x]=3728 Home Construction
        @Order(10)
        public static class ICB3728 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3728L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3728");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=3740 text=Leisure Goods values[x]=3740 Leisure Goods
      @Order(10)
      public static class ICB3740 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 3740L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB3740");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=3743 text=Consumer Electronics values[x]=3743 Consumer Electronics
        @Order(10)
        public static class ICB3743 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3743L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3743");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3745 text=Recreational Products values[x]=3745 Recreational Products
        @Order(10)
        public static class ICB3745 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3745L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3745");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3747 text=Toys values[x]=3747 Toys
        @Order(10)
        public static class ICB3747 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3747L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3747");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=3760 text=Personal Goods values[x]=3760 Personal Goods
      @Order(10)
      public static class ICB3760 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 3760L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB3760");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=3763 text=Clothing & Accessories values[x]=3763 Clothing & Accessories
        @Order(10)
        public static class ICB3763 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3763L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3763");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3765 text=Footwear values[x]=3765 Footwear
        @Order(10)
        public static class ICB3765 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3765L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3765");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3767 text=Personal Products values[x]=3767 Personal Products
        @Order(10)
        public static class ICB3767 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3767L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3767");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=3780 text=Tobacco values[x]=3780 Tobacco
      @Order(10)
      public static class ICB3780 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 3780L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB3780");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=3785 text=Tobacco values[x]=3785 Tobacco
        @Order(10)
        public static class ICB3785 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3785L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3785");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }
  }

  // code=4000 text=Health Care values[x]=4000 Health Care
  @Order(10)
  public static class ICB4000 extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final Long ID = 4000L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ICB4000");
    }

    @Override
    public Long getId() {
      return ID;
    }

    // code=4500 text=Health Care values[x]=4500 Health Care
    @Order(10)
    public static class ICB4500 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 4500L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB4500");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=4530 text=Health Care Equipment & Services values[x]=4530 Health Care Equipment & Services
      @Order(10)
      public static class ICB4530 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 4530L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB4530");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=4533 text=Health Care Providers values[x]=4533 Health Care Providers
        @Order(10)
        public static class ICB4533 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 4533L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB4533");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=4535 text=Medical Equipment values[x]=4535 Medical Equipment
        @Order(10)
        public static class ICB4535 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 4535L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB4535");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=4537 text=Medical Supplies values[x]=4537 Medical Supplies
        @Order(10)
        public static class ICB4537 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 4537L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB4537");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=4570 text=Pharmaceuticals & Biotechnology values[x]=4570 Pharmaceuticals & Biotechnology
      @Order(10)
      public static class ICB4570 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 4570L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB4570");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=4573 text=Biotechnology values[x]=4573 Biotechnology
        @Order(10)
        public static class ICB4573 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 4573L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB4573");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=4577 text=Pharmaceuticals values[x]=4577 Pharmaceuticals
        @Order(10)
        public static class ICB4577 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 4577L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB4577");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }
  }

  // code=5000 text=Consumer Services values[x]=5000 Consumer Services
  @Order(10)
  public static class ICB5000 extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final Long ID = 5000L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ICB5000");
    }

    @Override
    public Long getId() {
      return ID;
    }

    // code=5300 text=Retail values[x]=5300 Retail
    @Order(10)
    public static class ICB5300 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 5300L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB5300");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=5330 text=Food & Drug Retailers values[x]=5330 Food & Drug Retailers
      @Order(10)
      public static class ICB5330 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 5330L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB5330");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=5333 text=Drug Retailers values[x]=5333 Drug Retailers
        @Order(10)
        public static class ICB5333 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5333L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5333");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5337 text=Food Retailers & Wholesalers values[x]=5337 Food Retailers & Wholesalers
        @Order(10)
        public static class ICB5337 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5337L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5337");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=5370 text=General Retailers values[x]=5370 General Retailers
      @Order(10)
      public static class ICB5370 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 5370L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB5370");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=5371 text=Apparel Retailers values[x]=5371 Apparel Retailers
        @Order(10)
        public static class ICB5371 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5371L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5371");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5373 text=Broadline Retailers values[x]=5373 Broadline Retailers
        @Order(10)
        public static class ICB5373 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5373L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5373");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5375 text=Home Improvement Retailers values[x]=5375 Home Improvement Retailers
        @Order(10)
        public static class ICB5375 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5375L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5375");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5377 text=Specialized Consumer Services values[x]=5377 Specialized Consumer Services
        @Order(10)
        public static class ICB5377 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5377L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5377");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5379 text=Specialty Retailers values[x]=5379 Specialty Retailers
        @Order(10)
        public static class ICB5379 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5379L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5379");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }

    // code=5500 text=Media values[x]=5500 Media
    @Order(10)
    public static class ICB5500 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 5500L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB5500");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=5550 text=Media values[x]=5550 Media
      @Order(10)
      public static class ICB5550 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 5550L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB5550");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=5553 text=Broadcasting & Entertainment values[x]=5553 Broadcasting & Entertainment
        @Order(10)
        public static class ICB5553 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5553L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5553");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5555 text=Media Agencies values[x]=5555 Media Agencies
        @Order(10)
        public static class ICB5555 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5555L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5555");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5557 text=Publishing values[x]=5557 Publishing
        @Order(10)
        public static class ICB5557 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5557L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5557");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }

    // code=5700 text=Travel & Leisure values[x]=5700 Travel & Leisure
    @Order(10)
    public static class ICB5700 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 5700L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB5700");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=5750 text=Travel & Leisure values[x]=5750 Travel & Leisure
      @Order(10)
      public static class ICB5750 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 5750L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB5750");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=5751 text=Airlines values[x]=5751 Airlines
        @Order(10)
        public static class ICB5751 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5751L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5751");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5752 text=Gambling values[x]=5752 Gambling
        @Order(10)
        public static class ICB5752 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5752L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5752");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5753 text=Hotels values[x]=5753 Hotels
        @Order(10)
        public static class ICB5753 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5753L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5753");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5755 text=Recreational Services values[x]=5755 Recreational Services
        @Order(10)
        public static class ICB5755 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5755L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5755");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5757 text=Restaurants & Bars values[x]=5757 Restaurants & Bars
        @Order(10)
        public static class ICB5757 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5757L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5757");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5759 text=Travel & Tourism values[x]=5759 Travel & Tourism
        @Order(10)
        public static class ICB5759 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5759L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5759");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }
  }

  // code=6000 text=Telecommunications values[x]=6000 Telecommunications
  @Order(10)
  public static class ICB6000 extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final Long ID = 6000L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ICB6000");
    }

    @Override
    public Long getId() {
      return ID;
    }

    // code=6500 text=Telecommunications values[x]=6500 Telecommunications
    @Order(10)
    public static class ICB6500 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 6500L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB6500");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=6530 text=Fixed Line Telecommunications values[x]=6530 Fixed Line Telecommunications
      @Order(10)
      public static class ICB6530 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 6530L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB6530");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=6535 text=Fixed Line Telecommunications values[x]=6535 Fixed Line Telecommunications
        @Order(10)
        public static class ICB6535 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 6535L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB6535");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=6570 text=Mobile Telecommunications values[x]=6570 Mobile Telecommunications
      @Order(10)
      public static class ICB6570 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 6570L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB6570");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=6575 text=Mobile Telecommunications values[x]=6575 Mobile Telecommunications
        @Order(10)
        public static class ICB6575 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 6575L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB6575");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }
  }

  // code=7000 text=Utilities values[x]=7000 Utilities
  @Order(10)
  public static class ICB7000 extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final Long ID = 7000L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ICB7000");
    }

    @Override
    public Long getId() {
      return ID;
    }

    // code=7500 text=Utilities values[x]=7500 Utilities
    @Order(10)
    public static class ICB7500 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 7500L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB7500");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=7530 text=Electricity values[x]=7530 Electricity
      @Order(10)
      public static class ICB7530 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 7530L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB7530");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=7535 text=Electricity values[x]=7535 Electricity
        @Order(10)
        public static class ICB7535 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 7535L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB7535");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=7570 text=Gas, Water & Multiutilities values[x]=7570 Gas, Water & Multiutilities
      @Order(10)
      public static class ICB7570 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 7570L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB7570");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=7573 text=Gas Distribution values[x]=7573 Gas Distribution
        @Order(10)
        public static class ICB7573 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 7573L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB7573");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=7575 text=Multiutilities values[x]=7575 Multiutilities
        @Order(10)
        public static class ICB7575 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 7575L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB7575");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=7577 text=Water values[x]=7577 Water
        @Order(10)
        public static class ICB7577 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 7577L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB7577");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }
  }

  // code=8000 text=Financials values[x]=8000 Financials
  @Order(10)
  public static class ICB8000 extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final Long ID = 8000L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ICB8000");
    }

    @Override
    public Long getId() {
      return ID;
    }

    // code=8300 text=Banks values[x]=8300 Banks
    @Order(10)
    public static class ICB8300 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 8300L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB8300");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=8350 text=Banks values[x]=8350 Banks
      @Order(10)
      public static class ICB8350 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 8350L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB8350");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=8355 text=Banks values[x]=8355 Banks
        @Order(10)
        public static class ICB8355 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8355L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8355");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }

    // code=8500 text=Insurance values[x]=8500 Insurance
    @Order(10)
    public static class ICB8500 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 8500L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB8500");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=8530 text=Nonlife Insurance values[x]=8530 Nonlife Insurance
      @Order(10)
      public static class ICB8530 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 8530L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB8530");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=8532 text=Full Line Insurance values[x]=8532 Full Line Insurance
        @Order(10)
        public static class ICB8532 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8532L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8532");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=8534 text=Insurance Brokers values[x]=8534 Insurance Brokers
        @Order(10)
        public static class ICB8534 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8534L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8534");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=8536 text=Property & Casualty Insurance values[x]=8536 Property & Casualty Insurance
        @Order(10)
        public static class ICB8536 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8536L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8536");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=8538 text=Reinsurance values[x]=8538 Reinsurance
        @Order(10)
        public static class ICB8538 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8538L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8538");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=8570 text=Life Insurance values[x]=8570 Life Insurance
      @Order(10)
      public static class ICB8570 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 8570L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB8570");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=8575 text=Life Insurance values[x]=8575 Life Insurance
        @Order(10)
        public static class ICB8575 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8575L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8575");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }

    // code=8700 text=Financial Services values[x]=8700 Financial Services
    @Order(10)
    public static class ICB8700 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 8700L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB8700");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=8730 text=Real Estate values[x]=8730 Real Estate
      @Order(10)
      public static class ICB8730 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 8730L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB8730");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=8733 text=Real Estate Holding & Development values[x]=8733 Real Estate Holding & Development
        @Order(10)
        public static class ICB8733 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8733L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8733");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=8737 text=Real Estate Investment Trusts values[x]=8737 Real Estate Investment Trusts
        @Order(10)
        public static class ICB8737 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8737L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8737");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=8770 text=General Financial values[x]=8770 General Financial
      @Order(10)
      public static class ICB8770 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 8770L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB8770");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=8771 text=Asset Managers values[x]=8771 Asset Managers
        @Order(10)
        public static class ICB8771 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8771L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8771");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=8773 text=Consumer Finance values[x]=8773 Consumer Finance
        @Order(10)
        public static class ICB8773 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8773L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8773");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=8775 text=Specialty Finance values[x]=8775 Specialty Finance
        @Order(10)
        public static class ICB8775 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8775L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8775");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=8777 text=Investment Services values[x]=8777 Investment Services
        @Order(10)
        public static class ICB8777 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8777L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8777");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=8779 text=Mortgage Finance values[x]=8779 Mortgage Finance
        @Order(10)
        public static class ICB8779 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8779L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8779");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=8980 text=Equity Investment Instruments values[x]=8980 Equity Investment Instruments
      @Order(10)
      public static class ICB8980 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 8980L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB8980");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=8985 text=Equity Investment Instruments values[x]=8985 Equity Investment Instruments
        @Order(10)
        public static class ICB8985 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8985L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8985");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=8990 text=Nonequity Investment Instruments values[x]=8990 Nonequity Investment Instruments
      @Order(10)
      public static class ICB8990 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 8990L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB8990");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=8995 text=Nonequity Investment Instruments values[x]=8995 Nonequity Investment Instruments
        @Order(10)
        public static class ICB8995 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8995L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8995");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }
  }

  // code=9000 text=Technology values[x]=9000 Technology
  @Order(10)
  public static class ICB9000 extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final Long ID = 9000L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ICB9000");
    }

    @Override
    public Long getId() {
      return ID;
    }

    // code=9500 text=Technology values[x]=9500 Technology
    @Order(10)
    public static class ICB9500 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 9500L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB9500");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=9530 text=Software & Computer Services values[x]=9530 Software & Computer Services
      @Order(10)
      public static class ICB9530 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 9530L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB9530");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=9533 text=Computer Services values[x]=9533 Computer Services
        @Order(10)
        public static class ICB9533 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 9533L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB9533");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=9535 text=Internet values[x]=9535 Internet
        @Order(10)
        public static class ICB9535 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 9535L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB9535");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=9537 text=Software values[x]=9537 Software
        @Order(10)
        public static class ICB9537 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 9537L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB9537");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=9570 text=Technology Hardware & Equipment values[x]=9570 Technology Hardware & Equipment
      @Order(10)
      public static class ICB9570 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 9570L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB9570");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=9572 text=Computer Hardware values[x]=9572 Computer Hardware
        @Order(10)
        public static class ICB9572 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 9572L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB9572");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=9574 text=Electronic Office Equipment values[x]=9574 Electronic Office Equipment
        @Order(10)
        public static class ICB9574 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 9574L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB9574");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=9576 text=Semiconductors values[x]=9576 Semiconductors
        @Order(10)
        public static class ICB9576 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 9576L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB9576");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=9578 text=Telecommunications Equipment values[x]=9578 Telecommunications Equipment
        @Order(10)
        public static class ICB9578 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 9578L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB9578");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }
  }
}
